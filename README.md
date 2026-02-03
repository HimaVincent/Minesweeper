# 🧨 Minesweeper (Java Console Game)

A console-based implementation of the classic Minesweeper game, built in Java with a strong focus on clean architecture, separation of concerns and readable, maintainable code.

![minesweeper](/minesweeper.png)

## 🎮 Features

- Playable Minesweeper game in the terminal
- Configurable grid size
- Reveal cells using coordinates (row,col)
- Flag / unflag cells (row,col,F)
- Cascade reveal for empty cells
- Clear win and loss conditions
- Polished console UI:
  - bordered grid layout
  - coloured numbers and flags (ANSI)
  - large visual text for WIN and LOSS
- Option to play again after a game ends

## 🧠 Design & Architecture

This project is intentionally structured to keep game logic independent of the user interface.

### Separation of concerns

- Engine handles game logic
- Console handles rendering and input

## 📁 Project Structure

```
src/
└── minesweeper
├── engine
│ ├── Game.java
│ ├── Board.java
│ ├── Cell.java
│ ├── GameConfig.java
│ ├── GameState.java
│ ├── MoveResult.java
│ └── Action.java
└── console
├── ConsoleApp.java
└── ConsoleRenderer.java
```

## ▶️ How to Run

- Clone the repository
- Open the project in your IDE (VS Code / IntelliJ)
- Run ConsoleApp.java
- Follow the on-screen instructions

## Game Controls

- Reveal a cell
  row,col

- Flag or unflag a cell
  row,col,F

- Quit the game
  Q

## 🏆 Win & Loss

Win: Reveal all non-mine cells
Loss: Reveal a mine

- The board is shown
- A large visual message is displayed
  After each game, you can choose whether to play again

## 🛠 Technologies Used

Java
ANSI escape codes for terminal colouring

## ✨ Notes

Colours may vary depending on terminal support
The game remains fully playable even without colour support
Designed as a learning project with emphasis on clarity and structure
