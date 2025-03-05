## HTML to PDF Converter

### Purpose
The HTML to PDF Converter application is used to convert HTMl files to PDF.

### How it Works
- The application copies html files from the configured source directory to the configured target directory.
- The subdirectory structure of the source directory is recreated in the target directory
- Each conversion is logged in the "logs" subdirectory of the target directory

### Configuration
A configuration file should be created that looks like this:
```json
{
    "sourceDirectoryPath": "C:\\Temp\\Test\\in",
    "targetDirectoryPath": "C:\\Temp\\Test\\out"
}
```

### Execution

This configuration file path can be passed in as the first argument of the command line call to the .jar
ex.
- java -jar "C:\***\html_to_pdf_converter.jar" "C:\***\html_to_pdf_converter.jar"

If no first argument is specified, the UI will prompt you to select the configuration file.
