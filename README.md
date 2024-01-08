# skiko-repaint-issue

## The resize debounce causes skiko to not repaint content. 

https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/assets/14200189/e2c9084c-4eda-4ce0-a97d-6de20dfd855c



### How to use:

Run the app and resize the window.

### Expected behaviour:

In a resize state the content should not be updated, the last set text should be rendered in a text view. After 0.5s (see [here](https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/blob/5ca12a97e85b291c21acb5b276e38b3b0adb421c/src/main/kotlin/TextViewCompose.kt#L35)) from the last resize event the text view should display "Skiko canvas, size=..." with a window size. 
<img width="800" alt="image" src="https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/assets/14200189/e5ccb64c-4ea1-4c0c-a175-4a852a2622ac">

### Actual behaviour:

Window filled with a orange color (see [here](https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/blob/5ca12a97e85b291c21acb5b276e38b3b0adb421c/src/main/kotlin/TextSkikoViewHolder.kt#L24)).
<img width="800" alt="image" src="https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/assets/14200189/ee4ca554-2413-42e4-8e4e-74dac5d1d628">

### Hack:

Everything works as expected when the Compose Canvas is added to a composition, even if this Canvas is not visible (Canvas still should have a non-zero size). See [here](https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/blob/5ca12a97e85b291c21acb5b276e38b3b0adb421c/src/main/kotlin/TextViewCompose.kt#L51).  

Hack can be switched at a runtime with the "Repaint hack" checkbox.




## Structure of the project:

`TextSkikoView` - skiko renderer, common code. Just draws a text line.  
`TextSkikoViewHolder` - bridge between skiko and SWING. This is where the `TextSkikoView` lives.  
`TextView` - this is a `model` class that allows users to actually use the `TextSkikoView`. It allows to set text and size and see a result.  
`TextViewCompose.kt` - Composable function with the debouncer and the hack.

