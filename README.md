## To-Do App with Jetpack Compose MVVM - Android Development

[To-Do App with Jetpack Compose MVVM - Android Development](https://www.skillshare.com/classes/To-Do-App-with-Jetpack-Compose-MVVM-Android-Development/459437485)

[stevdza-san/To-Do-Compose](https://github.com/stevdza-san/To-Do-Compose)

- ROOM Database: To save and read the data from a local database, plus we are going to write some custom SQL queries as well.

- Compose Navigation: So we can navigate between our Screen Composables. 

- ViewModel: Which will contain all the logic needed to work with our app.

- Preference DataStore: To persist a simple key-value pairs.

- Dependency injection with Dagger-Hilt library.


### Stopped at lesson 38 (CRUD Operations - Display Snack Bar)
The lessons do not contemplate configuration changes (mobile rotation).
In fact, every time there is a configuration change (rotation of phone) the  


```
LaunchedEffect(key1 = action){
sharedViewModel.action.value = action
}
```

is triggered, because the action variable is assigned values on this rotation, because of the recomposition.

So, rotating the phone, adds tasks to the ListScreen. 