(function () {
    var cal, calendarDate, d, m, y;

    this.setDraggableEvents = function () {
        return $("#events .external-event").each(function () {
            var eventObject;
            eventObject = {
                title: $.trim($(this).text())
            };
            $(this).data("eventObject", eventObject);
            return $(this).draggable({
                zIndex: 999,
                revert: true,
                revertDuration: 0
            });
        });
    };

    setDraggableEvents();

    calendarDate = new Date();

    d = calendarDate.getDate();

    m = calendarDate.getMonth();

    y = calendarDate.getFullYear();

    cal = $(".full-calendar-demo").fullCalendar({
        header: {
            center: "title",
            left: "basicDay,basicWeek,month",
            right: "prev,today,next"
        },
        buttonText: {
            prev: "<span class=\"icon-chevron-left\"></span>",
            next: "<span class=\"icon-chevron-right\"></span>",
            today: "Today",
            basicDay: "Day",
            basicWeek: "Week",
            month: "Month"
        },
        droppable: true,
        editable: true,
        selectable: true,
        select: function (start, end, allDay) {
            return bootbox.prompt("Event title", function (title) {
                if (title !== null) {
                    cal.fullCalendar("renderEvent", {
                        title: title,
                        start: start,
                        end: end,
                        allDay: allDay
                    }, true);
                    return cal.fullCalendar('unselect');
                }
            });
        },
        eventClick: function (calEvent, jsEvent, view) {
            return bootbox.dialog({
                message: $("<form class='form'><label>Change event name</label></form><input id='new-event-title' class='form-control' type='text' value='" + calEvent.title + "' /> "),
                buttons: {
                    "delete": {
                        label: "<i class='icon-trash'></i> Delete Event",
                        className: "pull-left",
                        callback: function () {
                            return cal.fullCalendar("removeEvents", function (ev) {
                                return ev._id === calEvent._id;
                            });
                        }
                    },
                    success: {
                        label: "<i class='icon-save'></i> Save",
                        className: "btn-success",
                        callback: function () {
                            calEvent.title = $("#new-event-title").val();
                            return cal.fullCalendar('updateEvent', calEvent);
                        }
                    }
                }
            });
        },
        drop: function (date, allDay) {
            var copiedEventObject, eventClass, originalEventObject;
            originalEventObject = $(this).data("eventObject");
            originalEventObject.id = Math.floor(Math.random() * 99999);
            eventClass = $(this).attr('data-event-class');
            console.log(originalEventObject);
            copiedEventObject = $.extend({}, originalEventObject);
            copiedEventObject.start = date;
            copiedEventObject.allDay = allDay;
            if (eventClass) {
                copiedEventObject["className"] = [eventClass];
            }
            $(".full-calendar-demo").fullCalendar("renderEvent", copiedEventObject, true);
            if ($("#calendar-remove-after-drop").is(":checked")) {
                return $(this).remove();
            }
        },
        events: [
            {
                id: "event1",
                title: "All Day Event",
                start: new Date(y, m, 1),
                className: 'event-orange'
            }, {
                id: "event2",
                title: "Long Event",
                start: new Date(y, m, d - 5),
                end: new Date(y, m, d - 2),
                className: "event-red"
            }, {
                id: 999,
                id: "event3",
                title: "Repeating Event",
                start: new Date(y, m, d - 3, 16, 0),
                allDay: false,
                className: "event-blue"
            }, {
                id: 999,
                id: "event3",
                title: "Repeating Event",
                start: new Date(y, m, d + 4, 16, 0),
                allDay: false,
                className: "event-green"
            }, {
                id: "event4",
                title: "Meeting",
                start: new Date(y, m, d, 10, 30),
                allDay: false,
                className: "event-orange"
            }, {
                id: "event5",
                title: "Lunch",
                start: new Date(y, m, d, 12, 0),
                end: new Date(y, m, d, 14, 0),
                allDay: false,
                className: "event-red"
            }, {
                id: "event6",
                title: "Birthday Party",
                start: new Date(y, m, d + 1, 19, 0),
                end: new Date(y, m, d + 1, 22, 30),
                allDay: false,
                className: "event-purple"
            }
        ]
    });

}).call(this);

$(".chat .new-message").live('submit', function (e) {
    var chat, date, li, message, months, reply, scrollable, sender, timeago;
    date = new Date();
    months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    chat = $(this).parents(".chat");
    message = $(this).find("#message_body").val();
    $(this).find("#message_body").val("");
    if (message.length !== 0) {
        li = chat.find("li.message").first().clone();
        li.find(".body").text(message);
        timeago = li.find(".timeago");
        timeago.removeClass("in");
        var month = (date.getMonth() + 1);
        var date_day = (date.getDate());
        timeago.attr("title", "" + (date.getFullYear()) + "-" + (month < 10 ? '0' : '') + month + "-" + (date_day < 10 ? '0' : '' ) + date_day + " " + (date.getHours()) + ":" + (date.getMinutes()) + ":" + (date.getSeconds()) + " +0200");
        timeago.text("" + months[date.getMonth()] + " " + (date.getDate()) + ", " + (date.getFullYear()) + " " + (date.getHours()) + ":" + (date.getMinutes()));
        setTimeAgo(timeago);
        sender = li.find(".name").text().trim();
        chat.find("ul").append(li);
        scrollable = li.parents(".scrollable");
        $(scrollable).slimScroll({
            scrollTo: scrollable.prop('scrollHeight') + "px"
        });
        li.effect("highlight", {}, 500);
        reply = scrollable.find("li").not(":contains('" + sender + "')").first().clone();
        setTimeout((function () {
            date = new Date();
            timeago = reply.find(".timeago");
            timeago.attr("title", "" + (date.getFullYear()) + "-" + (month < 10 ? '0' : '') + month + "-" + (date_day < 10 ? '0' : '' ) + date_day + " " + (date.getHours()) + ":" + (date.getMinutes()) + ":" + (date.getSeconds()) + " +0200");
            timeago.text("" + months[date.getMonth()] + " " + (date.getDate()) + ", " + (date.getFullYear()) + " " + (date.getHours()) + ":" + (date.getMinutes()));
            setTimeAgo(timeago);
            scrollable.find("ul").append(reply);
            $(scrollable).slimScroll({
                scrollTo: scrollable.prop('scrollHeight') + "px"
            });
            return reply.effect("highlight", {}, 500);
        }), 1000);
    }
    return e.preventDefault();
});

$(".recent-activity .ok").live("click", function (e) {
    $(this).tooltip("hide");
    $(this).parents("li").fadeOut(500, function () {
        return $(this).remove();
    });
    return e.preventDefault();
});
$(".recent-activity .remove").live("click", function (e) {
    $(this).tooltip("hide");
    $(this).parents("li").fadeOut(500, function () {
        return $(this).remove();
    });
    return e.preventDefault();
});
$("#comments-more-activity").live("click", function (e) {
    $(this).button("loading");
    setTimeout((function () {
        var list;
        list = $("#comments-more-activity").parent().parent().find("ul");
        list.append(list.find("li:not(:first)").clone().effect("highlight", {}, 500));
        return $("#comments-more-activity").button("reset");
    }), 1000);
    e.preventDefault();
    return false;
});
$("#users-more-activity").live("click", function (e) {
    $(this).button("loading");
    setTimeout((function () {
        var list;
        list = $("#users-more-activity").parent().parent().find("ul");
        list.append(list.find("li:not(:first)").clone().effect("highlight", {}, 500));
        return $("#users-more-activity").button("reset");
    }), 1000);
    e.preventDefault();
    return false;
});

(function () {
    $("#daterange").daterangepicker({
        ranges: {
            Yesterday: [moment().subtract("days", 1), moment().subtract("days", 1)],
            "Last 30 Days": [moment().subtract("days", 29), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")]
        },
        startDate: moment().subtract("days", 29),
        endDate: moment(),
        opens: "left",
        cancelClass: "btn-danger",
        buttonClasses: ['btn', 'btn-sm']
    }, function (start, end) {
        return $("#daterange span").html(start.format("MMMM D, YYYY") + " - " + end.format("MMMM D, YYYY"));
    });

}).call(this);


$(".todo-list .new-todo").live('submit', function (e) {
    var li, todo_name;
    todo_name = $(this).find("#todo_name").val();
    $(this).find("#todo_name").val("");
    if (todo_name.length !== 0) {
        li = $(this).parents(".todo-list").find("li.item").first().clone();
        li.find("input[type='checkbox']").attr("checked", false);
        li.removeClass("important").removeClass("done");
        li.find("label.todo span").text(todo_name);
        $(".todo-list ul").first().prepend(li);
        li.effect("highlight", {}, 500);
    }
    return e.preventDefault();
});

$(".todo-list .actions .remove").live("click", function (e) {
    $(this).tooltip("hide");
    $(this).parents("li").fadeOut(500, function () {
        return $(this).remove();
    });
    e.stopPropagation();
    e.preventDefault();
    return false;
});

$(".todo-list .actions .important").live("click", function (e) {
    $(this).parents("li").toggleClass("important");
    e.stopPropagation();
    e.preventDefault();
    return false;
});

$(".todo-list .check").live("click", function () {
    var checkbox;
    checkbox = $(this).find("input[type='checkbox']");
    if (checkbox.is(":checked")) {
        return $(this).parents("li").addClass("done");
    } else {
        return $(this).parents("li").removeClass("done");
    }
});