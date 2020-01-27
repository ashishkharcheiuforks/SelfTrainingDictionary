# SelfTrainingDictionary
Application helps learn foreign words. You must add new words with translations and then learn them using the quiz.
There will be statstics, rangs of result. App will interact with some translations API

Stack of technologies:
- Kotlin
- Retrofit OkHttp
- Coroutines
- Room
- WorkManager
- Material design
- Architect components (ViewModel, LiveData)
- Navigation components
- Data Binding
- Dagger2
- Timber

Presentation architecture pattern is going to be MVVM.
App is going to be separated on feature modules.

App is going to contain screens(first plan):
- list of words
- add/edit word page(each word can be marked by tag)
- list of quizes(each quiz can contain some list words)
- create/edit quiz(there are some types of quiz)
- quiz page

tasks:
1. toolbar from activity to fragment
2. start quiz button on irregular page
3. quiz page with 4 answers and timer
4. algorithm of answers building
5. count right and wrong answers. 
6. final page with result + save history. analize history. percent of studing words
