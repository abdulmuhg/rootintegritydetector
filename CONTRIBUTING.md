# Contributing to Root Integrity Detector

Thank you for your interest in contributing! Here's how to get involved.

---

## Reporting Bugs

Use the [Bug Report](.github/ISSUE_TEMPLATE/bug_report.md) issue template. Please include:
- Steps to reproduce the issue
- Expected vs actual behavior
- Device info (Android version, rooted or stock)

---

## Suggesting Features

Use the [Feature Request](.github/ISSUE_TEMPLATE/feature_request.md) issue template. Describe the problem you want solved and your proposed solution.

> For significant changes (new native checks, new dependencies), open an issue first to discuss the approach before writing code.

---

## Submitting a Pull Request

1. Fork the repository and create a branch from `master`:
   ```bash
   git checkout -b feature/your-feature-name
   ```
2. Make your changes following the code style guidelines below
3. Verify the project builds cleanly:
   ```bash
   ./gradlew assembleDebug
   ```
4. Open a pull request using the PR template and fill in all checklist items

---

## Code Style

- **Kotlin**: Follow the [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- **C++**: C++17 standard; keep functions focused and under ~50 lines
- **No new dependencies** without opening an issue for discussion first
- Keep native checks self-contained in `native-lib.cpp` unless a refactor is agreed upon

---

## License

By contributing, you agree that your contributions will be licensed under the [MIT License](LICENSE).
