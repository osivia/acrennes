$JQry(function () {

    $JQry(".cua").each(function (index, element) {
        var $cua = $JQry(element);


        // Lazy loading
        $cua.find(".cua-placeholder").each(function (index, element) {
            var $placeholder = $JQry(element);

            loadApplications($cua, $placeholder);
        });


        $cua.find("[data-target='settings']").each(function (index, element) {
            var $link = $JQry(element);

            if (!$link.data("loaded")) {
                $link.click(function (event) {
                    // AJAX parameters
                    var container = null;
                    var options = {
                        requestHeaders: ["ajax", "true", "bilto"],
                        method: "post",
                        onSuccess: function (t) {
                            onAjaxSuccess(t, null);
                        }
                    };
                    var url = $cua.data("settings-url");
                    var eventToStop = null;
                    var callerId = null;

                    directAjaxCall(container, options, url, eventToStop, callerId);
                });

                $link.data("loaded", true);
            }
        });


        if (!$cua.data("loaded")) {
            // Other applications
            $cua.find(".collapse").each(function (index, element) {
                var $collapse = $JQry(element);

                $collapse.on("shown.bs.collapse", function () {
                    if (!$collapse.data("loaded")) {
                        var $placeholder = $collapse.find(".cua-placeholder-other-applications");

                        loadApplications($cua, $placeholder);

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


    function loadApplications($cua, $placeholder) {
        jQuery.ajax({
            async: false,
            cache: false,
            dataType: "html",
            global: false,
            url: $placeholder.data("url"),

            error: function (xhr, status, error) {
                $placeholder.html("<p class='text-danger'>" + $cua.data("error-message") + "</p>");
            },

            success: function (data, status, xhr) {
                $placeholder.html(data);
            }
        });
    }
});
