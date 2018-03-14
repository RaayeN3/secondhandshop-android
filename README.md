# Second Hand Shop App

## Overview
An app that enables the user to publish Adverts, display them in a ListView and do CRUD operations with them. Firstly, the user has 
to register (if they don't have an account), or login if they have already registered. Then they are redirected to the Home page, where
they can use the bottom menu or the app bar menu to navigate between activities. There are three types of Adverts - General, Fashion
and Car. Each of them has unique attributes and field types. The menus are conditional, meaning if there are no Adverts in the database,
the user won't be able to access the Browse page.

## Persistence approach
Login and Register screens are using cloud storage - Firebase. The device has to be connected to the internet in order to Login/Reg
and go beyond the Login/Register activities.

Data in the app is persisted using SharedPreferences. When the app is first started it will load 1 object of each type of Advert.
They're just example objects, added in for display purpose and they can be easily deleted. If the app is not running for the first 
time, it will load data from SharedPreferences. When onPause state is triggered, data is saved to SharedPreferences.

## Libraries used
- [ScrollableNumberPicker](https://github.com/michaelmuenzer/ScrollableNumberPicker)
- [Nammu](https://github.com/tajchert/Nammu)
- [GSON](https://github.com/google/gson)
- [Firebase](https://firebase.google.com/)
- [JUnit](https://junit.org/junit4/)

## References
- (https://stackoverflow.com/questions/34311601/how-to-add-a-bottom-menu-to-android-activity)
- (https://stackoverflow.com/questions/10266595/how-to-make-a-round-button)
- (https://www.youtube.com/channel/UCllewj2bGdqB8U9Ld15INAg)
- (https://github.com/codingdemos/SpinnerExample)
- (https://stackoverflow.com/questions/11535011/edittext-field-is-required-before-moving-on-to-another-activity)
- (https://stackoverflow.com/questions/3285412/limit-text-length-of-edittext-in-android)
- (https://github.com/tajchert/Nammu)
- (https://stackoverflow.com/questions/2558591/remove-listview-items-in-android)
- (https://freakycoder.com/android-notes-40-how-to-save-and-get-arraylist-into-sharedpreference-7d1f044bc79a)
- (https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/)
- (https://www.flaticon.com/)
