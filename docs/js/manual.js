var QueryString = function () {
    // This function is anonymous, is executed immediately and 
    // the return value is assigned to QueryString!
    var query_string = {};
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        // If first entry with this name
        if (typeof query_string[pair[0]] === "undefined") {
            query_string[pair[0]] = decodeURIComponent(pair[1]);
            // If second entry with this name
        } else if (typeof query_string[pair[0]] === "string") {
            var arr = [query_string[pair[0]], decodeURIComponent(pair[1])];
            query_string[pair[0]] = arr;
            // If third or later entry with this name
        } else {
            query_string[pair[0]].push(decodeURIComponent(pair[1]));
        }
    }
    return query_string;
}();

$('#index').load('https://docs.google.com/a/eduworks.com/document/d/1rWMAtIv161yVH2-4G16e0emqDM_-1CXBC66EtBMVlwE/pub?embedded=true', null, function () {
    $("style").remove();
    $("a").each(function () {
        if ($(this).attr("href") != null)
            if ($(this).attr("href").indexOf("https://www.google.com/url?q=https://docs.google.com/document/d/") != -1) {
                var start = "https://www.google.com/url?q=https://docs.google.com/document/d/".length;
                var cnt = $(this).attr("href").indexOf("&sa=") - start;
                $(this).attr("href", "/index.html?doc=" +
                    decodeURIComponent($(this).attr("href").substr(start, cnt).replace("/edit", "")).replace("heading=", ""));
            }
        if ($(this).attr("href").indexOf("https://www.google.com/url?q=https://docs.google.com/spreadsheets/d/") != -1) {
            var start = "https://www.google.com/url?q=https://docs.google.com/spreadsheets/d/".length;
            var cnt = $(this).attr("href").indexOf("&sa=") - start;
            $(this).attr("href", "/index.html?she=" +
                decodeURIComponent($(this).attr("href").substr(start, cnt).replace("/edit", "")).replace("heading=", ""));
        }
    });
});
if (QueryString["she"] != null) {
    $("#siteloader").html("<iframe style='width:100%;height:99%;'/>").children().attr("src", "https://docs.google.com/spreadsheets/d/" + QueryString["she"] + "/pubhtml?widget=true&amp;headers=false");
}

if (QueryString["doc"] == null)
    QueryString["doc"] = "1oOgY8m3APBrPhFUk0AtvVTAW8Y8o_0-dKCGnIa285UA";
if (QueryString["she"] == null && QueryString["doc"] != null)
    $('#siteloader').load('https://docs.google.com/document/d/' + QueryString["doc"] + '/pub?embedded=true', null, function () {
        $("style").remove();
        $("a").each(function () {
            if ($(this).attr("href") != null) {
                if ($(this).attr("href").indexOf("https://www.google.com/url?q=https://docs.google.com/document/d/") != -1) {
                    var start = "https://www.google.com/url?q=https://docs.google.com/document/d/".length;
                    var cnt = $(this).attr("href").indexOf("&sa=") - start;
                    $(this).attr("href", "/index.html?doc=" +
                        decodeURIComponent($(this).attr("href").substr(start, cnt).replace("/edit", "")).replace("heading=", ""));
                }
                if ($(this).attr("href").indexOf("https://www.google.com/url?q=https://docs.google.com/spreadsheets/d/") != -1) {
                    var start = "https://www.google.com/url?q=https://docs.google.com/spreadsheets/d/".length;
                    var cnt = $(this).attr("href").indexOf("&sa=") - start;
                    $(this).attr("href", "/index.html?she=" +
                        decodeURIComponent($(this).attr("href").substr(start, cnt).replace("/edit", "")).replace("heading=", ""));
                }
            }
        });
        if ($("#siteloader").html().trim() == "") {
            $("#siteloader").html("<center><img src='https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Wikidata_logo_under_construction_sign_wide.svg/1024px-Wikidata_logo_under_construction_sign_wide.svg.png'><br>This document has not yet been published. Please check back later.</center>");
        }
        $("p span").each(function () {
            if ($(this).html().trim() == "" && $(this).html().indexOf("&nbsp;") == -1) {
                $(this).html("&nbsp;");
            }
        });
        $("#siteloader").find("span").each(function () {
            if ($(this).parent().children("span").length > 1 && $(this).children("img").length == 1) {
                $(this).css("float", "right");

                $(this).css("margin-left", "1rem");
            }
        });
        $("p").each(function () {
            if ($(this).children("span").length >= 2 && $(this).children("span").first().text().trim().match(":$"))
                $(this).children("span").first().css("font-weight", "bold");
        });
        if (location.hash != null && location.hash != "")
            location.href = location.hash;
    });
$(document).foundation();
$("a[data-open]").click(function (ev) {
    ev.preventDefault();
});
