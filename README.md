# BakingApp

Project for the Udactity Android Developer Nanodegree (https://www.udacity.com) by Google.

This project shows the recipes and the preparation steps with their videos. To display videos I use MediaPlayer/Exoplayer.

The data will be downloaded from a server using Okhttp3 library.

The fragments will be used to create responsive design that workes on phone and tablets. 

## This project shows the following functionalities:

1. Recipes in RecyclerView in linear layout(vertical orientation) when the screen is portrait and a Gridview Orientation when the device is in landscape.

2. The ingredients and the steps of the recipe in a nested scrollview. This becomes a two pane UI when in landscape mode in tablet devices. It remains a single pane in phones (no matter what the orientation is).

3. There is a Idling resource test for the first loading of recipe from network and a click on an item of the RecyclerView.

4. Leverage third-party library also like Picasso (Image loading) and Butterknife von Jake Wharton(for Annotations).

5. Use can add a widget to store the ingredients of a recipe.

6. The user can navigate between recipes through previous/next buttons.

Please see here what the screens look like:

![Main View](https://github.com/vjauckus/PopMovies_Stage_2/blob/master/Main_View.png)

![Movies Details](https://github.com/vjauckus/PopMovies_Stage_2/blob/master/Movie_Details.png)
