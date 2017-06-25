(function() {
  $(document).ready(function() {
    if (localStorage.getItem("content") !== null) {
      $("#color-settings-body-color").attr("href", localStorage.getItem("content"));
    }
    if ((localStorage.getItem("contrast") !== null) && !$("body").hasClass("contrast-background")) {
      $("body")[0].className = $("body")[0].className.replace(/(^|\s)contrast.*?(\s|$)/g, " ").replace(/\s\s+/g, " ").replace(/(^\s|\s$)/g, "");
      $("body").addClass(localStorage.getItem("contrast"));
    }
    $(".color-settings-body-color > a").hover(function() {
      $("#color-settings-body-color").attr("href", $(this).data("change-to"));
      return localStorage.setItem("content", $(this).data("change-to"));
    });
    return $(".color-settings-contrast-color > a").hover(function() {
      $('body')[0].className = $('body')[0].className.replace(/(^|\s)contrast.*?(\s|$)/g, ' ').replace(/\s\s+/g, ' ').replace(/(^\s|\s$)/g, '');
      $('body').addClass($(this).data("change-to"));
      return localStorage.setItem("contrast", $(this).data("change-to"));
    });
  });

}).call(this);
