# Cloud-Based-ToDo-App
A To-Do android application which interacts with Firebase for storage and account info. 

*Functionalities of the app:*
- It can create new user and login to the cloud.
- Tasks are fetched and stored for every user. So when you login again the tasks are still there.
- A logout option is also present.

*Insights:*
- Double iteration for structure  enabled to iterate through the JSON nodes. And an if condition was placed to fetch specific user data.
- For storing tasks a new random key was generated for each tasks using push() method. This creates a new node for every tasks.
- Modal class was used to successfully get and set data and push to the list.
- onClickListener() was used to listen to the dynamic button in the list. The key is fetched from the selected data and is subsequently deleted from the database.
