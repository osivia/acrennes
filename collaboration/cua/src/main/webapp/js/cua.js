$JQry(function () {

    $JQry(".cua").each(function (index, element) {
        var $cua = $JQry(element);


        // Lazy loading
        $cua.find(".cua-placeholder").each(function (index, element) {
            var $placeholder = $JQry(element);

            $placeholder.load($placeholder.data("url"), function(response, status, xhr) {
            });
        });


        if (!$cua.data("loaded")) {
            // Other applications
            $cua.find(".collapse").each(function (index, element) {
                var $collapse = $JQry(element);

                $collapse.on("show.bs.collapse", function () {
                    if (!$collapse.data("loaded")) {
                        var $placeholder = $collapse.find(".cua-placeholder-other-applications");

                        $placeholder.load($placeholder.data("url"));

                        $collapse.data("loaded", true);
                    }
                })
            });


            // Sortable
            $cua.find(".cua-sortable").sortable({
                cancel: "a",
                connectWith: ".cua-sortable",
                cursor: "move",
                forcePlaceholderSize: true,
                tolerance: "pointer",

                stop: function (event, ui) {
                    var $item = ui.item;
                    var $form = $item.closest("form");
                    var $submit = $form.find("input[type=submit][name=reorder]");

                    $form.find(".cua-sortable").each(function (index, element) {
                        var $sortable = $JQry(element);
                        var starred = $sortable.data("starred");

                        $sortable.children().each(function (index, element) {
                            var $child = $JQry(element);
                            var $orderInput = $child.find("input[type=hidden][name$=order]");
                            var $starredInput = $child.find("input[type=hidden][name$=starred]");

                            $orderInput.val(index + 1);
                            $starredInput.val(starred);
                        });
                    });

                    $submit.click();
                }
            });

            $cua.data("loaded", true);
        }
    });
});
