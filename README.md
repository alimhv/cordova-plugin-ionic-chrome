
# cordova-plugin-ionic-chrome

Cordova plugin for opening Chrome Custom Tabs when the WebView version is not supported.

## Description

This Cordova plugin allows you to open Chrome Custom Tabs when the WebView version on the user's device is not supported. It checks the installed WebView version and, if necessary, opens Chrome Custom Tabs to provide a better browsing experience.

## Installation

To add this plugin to your Cordova project, use the following command:

```bash
cordova plugin add cordova-plugin-ionic-chrome
```

## Usage

The plugin will automatically detect the WebView version on the user's device. If the WebView version is below the specified minimum version, it will open Chrome Custom Tabs to handle web content.

### Configuration

In your Cordova project's `config.xml`, you can specify the minimum WebView version and the default URL to be opened in the WebView using the following preferences:

```xml
<widget ...>
    ...
    <preference name="minimum-webview-version" value="61" />
    <preference name="webview-url" value="https://example.com" />
    ...
</widget>
```

Replace `"61"` with the minimum supported WebView version, and `"https://example.com"` with the default URL you want to open in the WebView.

### JavaScript Usage

The plugin is designed to work automatically. There is no need to call any JavaScript function explicitly.

## Platform Support

- Android

## Known Limitations

- This plugin currently supports Android only.
- The plugin opens Chrome Custom Tabs only if the installed WebView version is below the specified minimum version.

## License

This plugin is released under the [MIT License](LICENSE).

## Issues

If you encounter any issues or have suggestions for improvements, please feel free to open an issue on the [GitHub repository](https://github.com/your-username/cordova-plugin-ionic-chrome/issues).

## Contributing

Pull requests are welcome. Please ensure you follow the [contribution guidelines](CONTRIBUTING.md) when making contributions.

---

Please make sure to replace the placeholders such as `"your-username"` with your GitHub username and update any other information specific to your plugin. The README.md file provides essential information about your plugin, its purpose, usage, and how to report issues or contribute to the project. It helps users and potential contributors understand your plugin better.
