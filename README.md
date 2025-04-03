<div align="center">
    
  # Compose Showcase

  <img src="https://img.shields.io/badge/platform-android-brightgreen.svg" alt="platform">
  <img src="https://img.shields.io/badge/license-MIT-green.svg" alt="license">
  <img src="https://github.com/jocoand/compose-showcase/actions/workflows/verify-screenshot-test.yml/badge.svg?branch=main" alt="status">

  <p>
    
  A library for showcasing your feature in Jetpack Compose.
  
  </p>

  
</div>

## 🍁 Sequence Showcase
<p>
<img src="https://img.shields.io/badge/mavenCentral-1.3.0-blue.svg" alt="version">

Creating a sequence of showcases in a specific order.

<img src="https://github.com/user-attachments/assets/c4826028-2699-4a70-ab83-edee811f3884" width="275" alt="preview">

<img src="https://github.com/user-attachments/assets/f32269a3-eb95-4b41-8cdc-d497e82271e3" width="273" alt="preview2">

</p>

### Installation
- Gradle
  ```
  implementation("io.github.jocoand:showcase-sequence:1.3.0")
  ```

### Usage
- #### Create your Showcase dialog

- ```
    @Composable
    fun MyShowcaseDialog(text: String, onClick: () -> Unit) {
        Column {
            Text(text = text)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onClick) {
                Text("Nice !")
            }
        }
    }
  ```
  or you can use our [Predefined Dialog](https://github.com/jocoand/compose-showcase/edit/main/README.md#dialog)
- #### Declare the `SequenceShowcase` & `sequenceShowcaseState`
- ```
  val sequenceShowcaseState = rememberSequenceShowcaseState()
  
  SequenceShowcase(state = sequenceShowcaseState) {
      Scaffold(
        ...
  ```

- #### Mark your target view (view to be highlighted) with `sequenceShowcaseTarget` modifier

- ```
    
    MyView1(    // View to be highlighted
      modifier = Modifier
          .sequenceShowcaseTarget(    // Mark with sequenceShowcaseTarget
              index = 0,
              content = {
                  MyShowcaseDialog(    // Dialog to be displayed
                      text = "Hey, this is Greetings showcase",
                  )
              }
          ),
      onClick = { sequenceShowcaseState.start() }
    )
  ```
  `index`: the order to be shown in the sequence
  
  `content`: dialog to be displayed

- #### Use `SequenceShowcaseState.start()` to start the showcase
- ```
    LaunchButton(
        ...
        onClick = { sequenceShowcaseState.start() }
    )
  ```
  `index`: value to start at certain index (optional)

- #### Use `SequenceShowcaseState.next()` to navigate to the next showcase
- ```
    MyView2(
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
- `position`
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/1e368c82-b301-4c8a-95f2-d76562686d2b" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/d1a791b9-791e-40cd-aac9-6ff0452a7584" width="200">
      </td>

    </tr>
    <tr>
      <td align="center">Top</td>
      <td align="center">Bottom</td>
    </tr>
  </table>

  `Default`: relative to target position

- `alignment`
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/bcc803b5-f570-43b7-bbfc-a00c3ae1ec5c" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/65695ac9-3fca-4b79-85b6-2d3d30e8d122" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/e4e5f045-cf92-4ef0-8570-a9410867ccbc" width="200">
      </td>
    </tr>
    <tr>
      <td align="center">Start</td>
      <td align="center">Center</td>
      <td align="center">End</td>
    </tr>
  </table>

  `Default`: relative to target position

- `highlight`
  <table>
      <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/8ba3932a-1137-42fc-a8dc-64a8868fed03" width="120">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/fc91cc26-2fe5-42c8-902e-0ae04a9257b7" width="120">
      </td>
    </tr>
    <tr>
      <td align="center">Rectangular</td>
      <td align="center">Circular</td>
    </tr>
  </table>

-  `animationDuration`: duration of the enter and exit animation.

### Inspired by 💡 
- https://github.com/canopas/compose-intro-showcase

### Sample 🎨 
- See [MainActivity](https://github.com/jocoand/compose-showcase/blob/main/app/src/main/java/com/joco/composeshowcase/MainActivity.kt)


## 🌀 ShowcaseView 
<img src="https://img.shields.io/badge/mavenCentral-1.4.4-blue.svg" alt="version">

In case you need more basic usage, you can you use [ShowcaseView](https://github.com/jocoand/compose-showcaseview/tree/main)

```
implementation("io.github.jocoand:showcase-sequence:1.4.4")
```

## 🏮 Dialog

### Arrow Dialog

A predefined dialog with arrow shape pointer.

<img width="280" alt="Screen Shot 2025-04-03 at 13 15 05" src="https://github.com/user-attachments/assets/8c46be99-dc26-4cef-b5a9-36071db4dce9" />

### Usage
```
.sequenceShowcaseTarget(
    ...
    .content  = {
        ArrowDialog(
            targetRect = it,
            content = { 
                // Your dialog content
            }
        )
    }
  ```

## Contributing
Contribution are welcome! 
Feel free to open an issue or a pull request, if you find any bugs or have any suggestions.
If you're new to the project, we recommend starting with a [`good first issue`](https://github.com/jocoand/compose-showcase/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22). that are tailored for first time contributors in the project.
