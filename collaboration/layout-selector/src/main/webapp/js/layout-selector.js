$JQry(function () {
    $JQry(".layout-selector").each(function (index, element) {
        var $container = $JQry(element);

        if (!$container.data("loaded")) {
            // Sortable
            $container.find(".layout-selector-sortable").sortable({
                cancel: "a",
                cursor: "move",
                forcePlaceholderSize: true,
                tolerance: "pointer",

                stop: function (event, ui) {
                    var $item = ui.item;
                    var $form = $item.closest("form");
                    var $submit = $form.find("input[type=submit][name=reorder]");

                    // $form.find(".cua-sortable").each(function (index, element) {
                    //     var $sortable = $JQry(element);
                    //     var starred = $sortable.data("starred");
                    //
                    //     $sortable.children().each(function (index, element) {
                    //         var $child = $JQry(element);
                    //         var $orderInput = $child.find("input[type=hidden][name$=order]");
                    //         var $starredInput = $child.find("input[type=hidden][name$=starred]");
                    //
                    //         $orderInput.val(index + 1);
                    //         $starredInput.val(starred);
                    //     });
                    // });

                    $submit.click();
                }
            });

            $container.data("loaded", true);
        }
    });
});
