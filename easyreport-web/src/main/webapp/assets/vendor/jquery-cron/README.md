# jQuery plugin: cron

jquery-cron is a [jQuery] plugin for 
presenting a simplified interface for users to specify cron entries.

Check out the [jquery-cron] website for more information.

There is much to be done on the flexibility and robustness front, 
and we welcome contributions and bug fixes. Feel free to fork 
and send us pull requests!

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
