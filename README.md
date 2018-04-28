# [Second Hand Shop App](https://github.com/cecobask/SecondHandShopApp)

## Overview
An app that enables the user to publish Adverts, display them in a ListView and do CRUD operations with them. Firstly, the user has 
to register (if they don't have an account), or login (by using email &amp; password or Google sign-in) if they have already registered.
Then they are redirected to the Home page, where they can see what adverts are currently in-store and click on the navigation buttons to view them.
There are three types of Adverts - General, Fashion and Car. Each of them has unique attributes and field types. The menus are conditional, meaning
if there are no Adverts in the database, the user won't be able to access the Browse page.

## Persistence approach
All the adverts' data is persisted in a cloud database - Firebase. They are retrieved in real-time. Authentication is made with Firebase Authentication
and Google Sign-in. The device has to be connected to the internet in order to Login/Reg and go beyond the Login/Register activities.

## Libraries used
- [ScrollableNumberPicker](https://github.com/michaelmuenzer/ScrollableNumberPicker)
- [Nammu](https://github.com/tajchert/Nammu)
- [GSON](https://github.com/google/gson)
- [Firebase](https://firebase.google.com/)
- [FirebaseUI](https://github.com/firebase/FirebaseUI-Android)
- [Glide](https://github.com/bumptech/glide)
- [JUnit](https://junit.org/junit4/)

## UX / DX approach adopted
User experience was enhanced through the use of bottom menu and app bar menu for easy navigation between the activities. Fields, like 
price, are set up as Scrollable Number Pickers, plus an extra EditText for more precise input. County fields are setup as AutoCompleteTextViews, as
in that way the user does not need to enter the whole county, they can simply enter the first letter and suggestions will pop up, which also prevents
false input. Each field's length is limited to different size, to prevent the input of too many symbols, that are not needed. For example, price field
only needs a length of 3-5, as this app is intended to sell only general, fashion and car items (second hand).
In Browse activity, there are 3 ListViews and there is only 1 displayed at a time, depending on the selected radio button (advert type). The user is
able to quickly find an advert with the search function.

On developer experience side, I made sure everything is neatly organised and easy to follow. I utilised MVC (Model/View/Controller)
design pattern. They are all separated into their own packages. Code is thoroughly commented and formatted. Base class is used to keep
all the methods and features shared across different activities.

## Git approach adopted
I regularly committed changes to GitHub. I did not follow a pattern with my commits. Commits were done whenever I fix a problem or 
introduce new features. The way I committed was through the Android Studio UI - VCS.

## Author
**Tsvetoslav Dimov**  
*Waterford Institute of Technology*  
[LinkedIn](https://www.linkedin.com/in/cecobask/)
[GitHub](https://github.com/cecobask)

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
- (https://github.com/firebase/FirebaseUI-Android)
- (https://www.youtube.com/watch?v=b_tz8kbFUsU)
- (https://stackoverflow.com/questions/45232608/how-to-load-image-into-imageview-from-url-using-glide-v4-0-0rc1)
- (https://www.youtube.com/watch?v=Zy2DKo0v-OY)
- (https://github.com/bumptech/glide)
- (https://www.youtube.com/watch?v=-ywVw2O1pP8)
- (https://www.shareicon.net/)
