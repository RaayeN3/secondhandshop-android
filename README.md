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

## UX / DX approach adopted
User experience was enhanced through the use of bottom menu and app bar menu for easy navigation between the activities. Fields, like 
price, are set up as Scrollable Number Pickers, plus an extra EditText for quicker input. Location fields are setup as Spinners, as
in that way the user doesn't need to type in what their location is, they can simply select from the Spinner items, which also prevents
false input. Each field's length is limited to different size, to prevent the input of too many symbols, that are not needed. For example, price field only needs a length of 3-5, as this app is intended to sell only general, fashion and car items (second hand).
In Browse activity, there are 3 ListViews and there is only 1 displayed at a time, depending on the selected radio button (advert type).

On developer experience side, I made sure everything is neatly organised and easy to follow. I utilised MVC (Model/View/Controller)
design pattern. They are all separated into their own packages. Code is thoroughly commented and formatted. Base class is used to keep
all the methods and features shared across different activities.

## Git approach adopted
I regurarly commited changes to GitHub. I did not follow a pattern with my commits. Commits were done whenever I fix a problem or 
introduce new features. The way I commited was through the Android Studio UI - VCS.

## Author
**Tsvetoslav Dimov**  
*Waterford Institute of Technology*  
[LinkedIn](https://www.linkedin.com/in/cecobask/)

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
