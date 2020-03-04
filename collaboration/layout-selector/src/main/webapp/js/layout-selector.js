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
                    var $sortable = $form.find(".layout-selector-sortable");
                    var $submit = $form.find("input[type=submit][name=reorder]");

                    $sortable.children().each(function (index, element) {
                        var $child = $JQry(element);
                        var $input = $child.find("input[type=hidden][name$=order]");

                        $input.val(index + 1);
                    });

                    $submit.click();
                }
            });


            $container.find("select.select2.select2-layout-selector").each(function (index, element) {
                var $select2 = $JQry(element);
                var url = $select2.data("url");
                var options = {
                    minimumInputLength: 3,
                    theme: "bootstrap4",
                    width: "resolve"
                };

                options["ajax"] = {
                    url: url,
                    dataType: "json",
                    delay: 1000,
                    data: function (params) {
                        return {
                            filter: params.term,
                            page: params.page
                        };
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;

                        return {
                            results: data.items,
                            pagination: {
                                more: (params.page * data.pageSize) < data.total
                            }
                        };
                    },
                    cache: true
                };

                // Internationalization
                options["language"] = {};
                if ($select2.data("input-too-short") !== undefined) {
                    options["language"]["inputTooShort"] = function () {
                        return $select2.data("input-too-short");
                    }
                }
                if ($select2.data("error-loading") !== undefined) {
                    options["language"]["errorLoading"] = function () {
                        return $select2.data("error-loading");
                    }
                }
                if ($select2.data("loading-more") !== undefined) {
                    options["language"]["loadingMore"] = function () {
                        return $select2.data("loading-more");
                    }
                }
                if ($select2.data("searching") !== undefined) {
                    options["language"]["searching"] = function () {
                        return $select2.data("searching");
                    }
                }
                if ($select2.data("no-results") !== undefined) {
                    options["language"]["noResults"] = function () {
                        return $select2.data("no-results");
                    }
                }


                // Force width to 100%
                $select2.css({
                    width: "100%"
                });


                $select2.select2(options);
            });


            $container.data("loaded", true);
        }
    });
});
