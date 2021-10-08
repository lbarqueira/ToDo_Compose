## To-Do App with Jetpack Compose MVVM - Android Development

Following a tutorial by [stevdza-san](https://github.com/stevdza-san)  

[To-Do App with Jetpack Compose MVVM - Android Development](https://www.skillshare.com/classes/To-Do-App-with-Jetpack-Compose-MVVM-Android-Development/459437485)

[stevdza-san/To-Do-Compose](https://github.com/stevdza-san/To-Do-Compose)

- ROOM Database: To save and read the data from a local database, plus we are going to write some custom SQL queries as well.

- Compose Navigation: So we can navigate between our Screen Composables. 

- ViewModel: Which will contain all the logic needed to work with our app.

- Preference DataStore: To persist a simple key-value pairs.

- Dependency injection with Dagger-Hilt library.

### See open Issues for contribution

_The problem: as you may notice the ViewModel is huge!!!_

How to turn the Architecture of this app Clean through the use of:

- MVVM together with Clean Architecture concepts
- UseCases, one UseCase per feature
- One ViewModel per Screen
- One Repository per screen
- View states

Also maintaining:

- Hilt for dependency injection (already in use)
- Room for database

Add-ons:

- Multi-module
- Tests

### Contributing to an open issue
[Step-by-step guide to contributing on GitHub](https://www.dataschool.io/how-to-contribute-on-github/)

From the project homepage on GitHub you can click the Issues tab to navigate to a list of the open issues.
Issues are properlly labelled in order to choose the ones that you might want to work on.
You can click on an issue to view more detail and to determine if it’s something you’d like to work on.
If you`d like to have a go at working on an issue, it is good practice and etiquette to leave a comment you plan to work on.

- You can start by star :star: and fork this repository (located at the top right corner of this repository);
- By forking it, you are doing a copy of this repository, which will allows you to freely experiment with changes without 
  affecting the original project;
- Clone your forked repo. By clonning you are doing a copy of an existing repository (in this case, the forked repo) 
  into a new local directory, create the remote tracked branches, and checkout an initial branch locally. 
  By default, clone will create a reference to the remote repository called origin.
``` 
git clone https://github.com/lbarqueira/ToDo_Compose.git
```
- When beginning work on an issue locally, the first thing you’ll need to do is to create a branch for that piece of work. 
  To create and checkout a branch you can use the following command. 
  This command allows us to specify a name for our new branch and immediately *check it out* so we can work on it.  
  I tend to reference the issue number on my branch names.
```
git checkout -b <branchname>
```
- Once we are on our new branch we can make changes to the code which address the issue.
- Once we have made the required changes that address a particular issue, we need to commit that code to our branch. 
  We can use the “git status” command to view the changes since our last commit.
- We then use the “git add” command to stage the changes for the next commit.
```
git add .
```
- Next we will commit our staged changes using the “git commit” command.
```
git commit -m "A short descriptive message" 
```
- At this point we have made and committed out changes local to our development machine. 
  Our final step is to push the changes to our fork of the repository up on GitHub. 
  We can do that using the “git push” command. 
  We need to specify the name of the remote that we want to push to and the name of the branch we want to push up.
```
git push origin <branchname>
```
- Next, open your forked repo in your browser,  and you should see an automatic suggestion from GitHub to _Compare & pull request_. 
  Click it to begin create a pull request from your new branch.
- Create the pull request: Before submitting the pull request, you first need to describe the changes you made, 
  and in the title make sure to mention the issue number.
- From here on I suggest reading [Step-by-step guide to contributing on GitHub](https://www.dataschool.io/how-to-contribute-on-github/) 

