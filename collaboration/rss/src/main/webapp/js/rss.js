$JQry(function () {

    $JQry("select.select2.select2-rss").each(function (index, element) {
        var $element = $JQry(element);
        var url = $element.data("url");
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
        if ($element.data("input-too-short") !== undefined) {
            options["language"]["inputTooShort"] = function () {
                return $element.data("input-too-short");
            }
        }
        if ($element.data("error-loading") !== undefined) {
            options["language"]["errorLoading"] = function () {
                return $element.data("error-loading");
            }
        }
        if ($element.data("loading-more") !== undefined) {
            options["language"]["loadingMore"] = function () {
                return $element.data("loading-more");
            }
        }
        if ($element.data("searching") !== undefined) {
            options["language"]["searching"] = function () {
                return $element.data("searching");
            }
        }
        if ($element.data("no-results") !== undefined) {
            options["language"]["noResults"] = function () {
                return $element.data("no-results");
            }
        }


        // Force width to 100%
        $element.css({
            width: "100%"
        });

        $element.select2(options);

    });

    
    $JQry(".toutatice-slider").each(function(index, element) {
		var $element = $JQry(element);
		
		if (!$element.data("loaded")) {
			var $carousel = $element.find(".carousel");
			
			$carousel.on("slide.bs.carousel", function(event) {
				var to = event.to;
				var $indicators = $carousel.find(".toutatice-slider-indicators");
				var $children = $indicators.find("ol").children();
				
				$children.removeClass("active");
				$children.eq(to).addClass("active");
			});
						
			$element.data("loaded", true);
		}
    });
    
});
