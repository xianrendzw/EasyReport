# jQuery plugin: cron (Modified for Quartz Format)

jquery-cron is a [jQuery] plugin for
presenting a simplified interface for users to specify cron entries.

This version is modified to use Quartz Format.

Check out the [jquery-cron] website for more information.

There is much to be done on the flexibility and robustness front,
and we welcome contributions and bug fixes. Feel free to fork
and send us pull requests!

## Modifications made

This version of the library is modified to use Quartz cron format.
The library is still limited to the original functionality meaning
that I have only converted what was allready implemented into Quartz format.

When using the plugin with this Quartz Format you must always supply
seconds which is mandatory in the Quartz Format. Read more about
quartz cron expressions at

[quartz]: http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger "quartz cron expressions"

The allowed input into jquery-cron (for quartz) is as follows:

* Every minute    : 0 0/1 * * * ?
* Every hour at   : 0 - 0/1 * * ?
* Every day at    : 0 - - * * ?
* Every week at   : 0 - - ? * -
* Every month at  : 0 - - - * ?
* Every year at   : 0 - - - - ? *

Where "-" indicates where you can insert desired times. For example:

* 0 5 0/1 * * ?    => Every hour, five minutes past the hour

* 0 5 10 1 1 ? *   => 5 minutes past 10 at the first of January every year.

Note that this is useful information when passing initial data into jquery-cron.
When receiving output from jquery-cron the format is allready set.

## Dependencies

 * [jQuery]
 * [jquery-gentleSelect] (optional)

## Usage

To use this plugin, one simply needs to load [jQuery]
and the JS/CSS scripts for jquery-cron, then attach it an empty `<DIV>`
on DOM ready:

    $(document).ready(function() {
        $('#selector').cron();
    });
    </script>

For more options, see the [jquery-cron] website.


## Others

Copyright (c) 2010-2013, Shawn Chin.

This project is licensed under the [MIT license].

 [jQuery]: http://jquery.com "jQuery"
 [jquery-cron]: http://shawnchin.github.com/jquery-cron "jquery-cron"
 [jquery-gentleSelect]: http://shawnchin.github.com/jquery-gentleSelect "jquery-gentleSelect"
 [MIT License]: http://www.opensource.org/licenses/mit-license.php "MIT License"
