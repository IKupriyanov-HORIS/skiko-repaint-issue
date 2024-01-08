# skiko-repaint-issue

## The resize debounce causes skiko to not repaint content. 

https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/assets/14200189/e2c9084c-4eda-4ce0-a97d-6de20dfd855c



### How to use:

Run app and resize the window.

### Expected behaviour:

After 0.5s from the last resize event the window should display text "Skiko canvas, size=..." with actual size.  
<img width="800" alt="image" src="https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/assets/14200189/e5ccb64c-4ea1-4c0c-a175-4a852a2622ac">

### Actual behaviour:

Window filled with orange color (background color of the `TextSkikoViewHolder` (TextSkikoViewHolder.kt:24)).
<img width="800" alt="image" src="https://github.com/IKupriyanov-HORIS/skiko-repaint-issue/assets/14200189/ee4ca554-2413-42e4-8e4e-74dac5d1d628">

### Hack:

Found strange fix - everything works as expected when add Compose Canvas to composition, even if this Canvas is hidden from composition (yet Canvas size should not be zero).  
Hack can be switched at runtime by the checkbox "Repaint hack".




## Structure of the project:

`TextSkikoView` - skiko renderer, common code. Just draws a text line.  
`TextSkikoViewHolder` - bridge between skiko and SWING. This is where the `TextSkikoView` lives.  
`TextView` - this is a `model` class that allows users to actually use the `TextSkikoView`. It allows to set text and size and see a result.  
`TextViewCompose.kt` - Composable function with the debouncer and the hack.

