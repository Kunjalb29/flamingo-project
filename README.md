# 🏆 Predict & Win — Gold Price Prediction Feature

A production-ready, single-screen Android application built with **Kotlin**, **Jetpack Compose**, and **Material Design 3**. Users predict whether tomorrow's gold price will go **UP** or **DOWN**, track their progress in a 7-day challenge, and earn reward milestones.

> **No external APIs required** — the app uses static/mock data for all displayed values.

---

## 📸 Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Header & Gold Price** | Displays the "Predict & Win" title and the current gold price (₹6,245.50 / gm) with a change badge |
| 2 | **7-Day Challenge Card** | Animated progress bar, day-dot indicators, and stats (Correct / Wrong / Streak) |
| 3 | **Reward Milestones** | Three milestone cards (3 → Feather, 5 → ₹50 Gold, 7 → ₹500 Gold) with glowing borders for unlocked items |
| 4 | **Community Sentiment** | Split gradient progress bar showing Bullish (65%) vs Bearish (35%) sentiment |
| 5 | **Prediction Selector** | Two large UP / DOWN cards with animated border & background on selection — only one selectable at a time |
| 6 | **CTA Button** | "Predict & Invest ₹50" — disabled until a direction is chosen |
| 7 | **Success Bottom Sheet** | Material 3 `ModalBottomSheet` confirming the prediction with direction badge and details |

---

## 🛠 Requirements

| Tool | Minimum Version |
|------|----------------|
| **Android Studio** | Ladybug (2024.2.1) or newer |
| **Kotlin** | 2.0.21 |
| **Android Gradle Plugin** | 8.7.3 |
| **Gradle** | 8.9 |
| **Min SDK** | 26 (Android 8.0) |
| **Target / Compile SDK** | 35 (Android 15) |
| **JDK** | 17 |

---

## 🚀 Setup & Run

### 1. Clone the Repository

```bash
git clone https://github.com/Kunjalb29/flamingo-project.git
cd flamingo-project
```

### 2. Open in Android Studio

1. Launch **Android Studio**.
2. Select **File → Open** and navigate to the cloned `flamingo-project` directory.
3. Wait for Gradle sync to complete (Android Studio will download all dependencies automatically).

### 3. Run the App

1. Connect a physical Android device (USB debugging enabled) or start an **AVD emulator** (API 26+).
2. Click the **▶ Run** button in Android Studio (or press `Shift + F10`).
3. The app will build and deploy to the selected device.

### 4. Build the APK

#### Debug APK
```bash
./gradlew assembleDebug
```
The APK will be at:
```
app/build/outputs/apk/debug/app-debug.apk
```

#### Release APK (requires signing config)
```bash
./gradlew assembleRelease
```

> **Note:** For release builds, you need to configure a signing key in `app/build.gradle.kts`. See the [Android signing docs](https://developer.android.com/studio/publish/app-signing) for details.

---

## 🏗 Architecture & Design

### Project Structure

```
app/src/main/java/com/flamingo/predictandwin/
├── MainActivity.kt                    # Entry point — hosts Compose content
├── data/
│   └── MockData.kt                    # Data classes & static mock values
└── ui/
    ├── theme/
    │   ├── Color.kt                   # Color palette (gold, semantic, surfaces)
    │   └── Theme.kt                   # Material 3 theme (dark color scheme + typography)
    └── screen/
        └── PredictAndWinScreen.kt     # Full screen UI with all 7 sections
```

### Key Concepts

#### State Management

The screen uses Compose's built-in state primitives — no ViewModel or external state library is needed for this single-screen scope:

```kotlin
var selectedDirection by remember { mutableStateOf<PredictionDirection?>(null) }
var showBottomSheet  by remember { mutableStateOf(false) }
```

- `selectedDirection` — tracks which button (UP / DOWN) is selected; `null` means neither.
- `showBottomSheet` — controls the visibility of the success `ModalBottomSheet`.

The CTA button reads `selectedDirection` to derive its `enabled` state, and the bottom sheet reads it to display the chosen direction.

#### State Hoisting

The root `PredictAndWinScreen` composable owns all mutable state and passes values + callbacks **down** to child composables. For example:

```kotlin
PredictionSelector(
    selected = selectedDirection,        // state ↓
    onSelect = { selectedDirection = it } // event ↑
)
```

This ensures child composables are **stateless** and **reusable**.

#### Compose Layout Strategy

| Layout | Used For |
|--------|----------|
| `Column` + `verticalScroll` | Main scrollable container |
| `Row` with `Arrangement.spacedBy` | Horizontal groups (stats, milestones, buttons) |
| `Card` + `RoundedCornerShape` | Elevated content sections |
| `Box` with `Modifier.clip` + `background` | Custom badges, progress bars, circular indicators |
| `Modifier.weight()` | Proportional sizing (sentiment bar, direction buttons) |

#### Animations

- **`animateFloatAsState`** — smoothly animates progress bars and background alpha.
- **`animateColorAsState`** — transitions border colors when selecting UP / DOWN.
- **`rememberInfiniteTransition`** — creates a pulsing glow effect on unlocked milestone borders.
- All animations use `tween` with `FastOutSlowInEasing` for a polished feel.

#### Material Design 3

- **Color Scheme**: Custom `darkColorScheme` with gold-branded primary colors.
- **Typography**: Full `Typography` object with consistent font weights and sizes.
- **Components**: `Card`, `Button`, `LinearProgressIndicator`, `ModalBottomSheet`, `Icon` — all from `material3`.
- **Shapes**: `RoundedCornerShape` with 12–16 dp corner radius throughout.

---

## 📦 Dependencies

All dependencies are managed through the **Compose BOM** (`2024.12.01`) for version alignment:

| Dependency | Purpose |
|-----------|---------|
| `androidx.activity:activity-compose` | Compose integration with Activity |
| `androidx.compose:compose-bom` | Version alignment for all Compose libraries |
| `androidx.compose.material3:material3` | Material Design 3 components |
| `androidx.compose.material:material-icons-extended` | Extended icon set (TrendingUp, Fire, etc.) |
| `androidx.compose.ui:ui-tooling-preview` | `@Preview` support in Android Studio |

---

## 📝 License

This project is created for educational / assignment purposes.

---

## 👤 Author

**Kunjal B** — [GitHub](https://github.com/Kunjalb29)
