<div align="center">
    
  # Compose Showcase

  <img src="https://img.shields.io/badge/mavenCentral-1.1.0-blue.svg" alt="version">
  <img src="https://img.shields.io/badge/license-MIT-green.svg" alt="license">
  <img src="https://img.shields.io/badge/platform-android-brightgreen.svg" alt="platform">
  
  <p>
    
  A Jetpack Compose library for showcasing your feature.
  
  </p>
  
</div>

## Sequence Showcase 🍁
Creating a sequence of showcases in a specific order.
<p>
   <img src="https://github.com/user-attachments/assets/c2fc64f8-3467-46c5-a638-eb439b264e70" width="300" alt="preview">
</p>

### Installation
- Gradle
  ```
  implementation("io.github.jocoand:showcase-sequence:1.0.0")
  ```

### Usage
- Create your Showcase dialog
- ```
    @Composable
    fun MyShowcaseDialog(text: String, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onClick
            ) {
                Text("Nice !")
            }
        }
    }
  ```
- Declare the `SequenceShowcase` & `sequenceShowcaseState`
- ```
  val sequenceShowcaseState = rememberSequenceShowcaseState()
  
  SequenceShowcase(state = sequenceShowcaseState) {
      Scaffold(
        ...
  ```

- Mark your target component with `sequenceShowcaseTarget` modifier

- ```
    // Component to be highlighted
    Greeting(
      modifier = Modifier
          .sequenceShowcaseTarget(
              index = 0,
              content = {
                  MyShowcaseDialog(
                      text = "Hey, this is Greetings showcase",
                  )
              }
          ),
      name = "Andy",
      onClick = { sequenceShowcaseState.start() }
    )
  ```
  Assign `index` value to mark the order to be shown in the sequence
  
  Assign `content` value with your dialog

- Use `SequenceShowcaseState.start()` to start the showcase
- ```
    LaunchButton(
        ...
        onClick = { sequenceShowcaseState.start() }
    )
  ```
  Assign optional `index` value to start at certain index

- Use `SequenceShowcaseState.next()` to navigate to the next showcase
- ```
    Article(
        modifier = Modifier
            .sequenceShowcaseTarget(
                index = 1,
                content = {
                    MyShowcaseDialog(
                        text = "Hey, this is Article show case",
                        onClick = {
                          sequenceShowcaseState.next() // Navigate to next showcase
                        } 
                    )
                }
            )
    )
  ```

- You can also dismiss the the showcase using `dimiss()`

- See [sample](https://github.com/jocoand/compose-showcase/blob/main/app/src/main/java/com/joco/composeshowcase/MainActivity.kt) for more more details

### Config
- You can adjust the dialog `position` and `alignment` from the `sequenceShowcaseTarget` modifier
- ```
    .sequenceShowcaseTarget(
        index = 2,
        position = ShowcasePosition.Top,
        alignment = ShowcaseAlignment.CenterHorizontal,
        content = { ... }
    )
  ```
  See the [preview](https://github.com/jocoand/compose-showcaseview?tab=readme-ov-file#config)

### 💡 Inspired by
- https://github.com/canopas/compose-intro-showcase

### 🎨 Sample
- See https://github.com/jocoand/compose-showcase/blob/main/app/src/main/java/com/joco/composeshowcase/MainActivity.kt

