# FOCR SDK Android



## 1. Install OCR SDK
## 2. SDK Integration
#### Initialize SDK
**java:**
```java
@Override
public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
  super.onCreate(savedInstanceState, persistentState);
    ...
  OCRManager.init(getApplicationContext());
}
```
**kotlin:**
```kotlin
override fun onCreate() {
  super.onCreate()
  ...
  OCRManager.init(applicationContext)
}
```
#### Init gateway
**java:**
```java
OCRManager.initGateway(appId, secretKey, new IInitGatewayCallback() {
  @Override
  public void onSuccess() {

  }

  @Override
  public void onFail(@Nullable AppException error) {

  }
});
```
**kotlin:**
```kotlin
OCRManager.initGateway(appId, secretKey, object : IInitGatewayCallback {
  override fun onSuccess() {

  }

  override fun onFail(error: AppException?) {

  }
})
```
#### Register OCR callback
**After registration, the SDK will return the corresponding status in the callback**
| Status     | Description |
| ----------- | ----------- |
| onStart      | Called at start OCR       |
| onSuccess      | Called when return result       |
| onFail   | Called when an error occurs in process  |
**java:**
```java
OCRManager.registerOCRCallback(new IOCRCallback() {
  @Override
  public void onStart() {

  }

  @Override
  public void onFail(@Nullable AppException error) {

  }

  @Override
  public void onSuccess(@NonNull TransformData result) {

  }
});
```
**kotlin:**
```kotlin
OCRManager.registerOCRCallback(object : IOCRCallback {
  override fun onStart() {

  }


  override fun onFail(error: AppException?) {

  }

  override fun onSuccess(result: TransformData) {

  })
})
```
## 3. SDK Features
#### Get config
* Get list config used to ocr param
* When sucess, result is `listConfig`, it will be called on `onSuccess()` in callback
* When fail, it will called on `onFail()` in callback
  **java:**
```java
OCRManager.getConfig(new IOCRConfigCallback() {
    @Override
    public void onSuccess(@NonNull List<OCRConfigData> listConfig) {
        
    }

    @Override
    public void onFail(@Nullable AppException error) {

    }
});
```
**kotlin:**
```kotlin
OCRManager.getConfig(object : IOCRConfigCallback {
    override fun onSuccess(listConfig: List<OCRConfigData>) {

    }

    override fun onFail(error: AppException?) {
    
    }
})
```
#### Start OCR
* Used to start OCR
* When successful start, it will be called on `onStart()` in callback
* When have result `result` will be return on `onSuccess` in callback.
* When fails, it will be processed at callback `onFail()` in callback.

**java:**
```java
//use file
OCRManager.startOCR(documentType, responseFormat, toMathMl, file); 
...
//use file path
OCRManager.startOCR(documentType, responseFormat, toMathMl, absoluteFilePath); 
```
**kotlin:**
```kotlin
//use file
OCRManager.startOCR(documentType, responseFormat, toMathMl, file) 
...
 //use file path
OCRManager.startOCR(documentType, responseFormat, toMathMl, absoluteFilePath)
```
#### Export data
* Used to export data OCR to csv or excel file
* `exportType` is `OCRExportType.CSV` or `OCRExportType.EXCEL`
* When export success `data` will be return on `onSuccess` in callback.
* When fails, it will be processed at callback `onFail()` in callback.
  **java:**
```java
OCRManager.export(requestId, exportType, new IExportCallback() {
    @Override
    public void onSuccess(@NonNull String data) {
        
    }

    @Override
    public void onFail(@Nullable AppException error) {

    }
});
```
**kotlin:**
```kotlin
OCRManager.export(requestId, exportType, object : IExportCallback {
    override fun onSuccess(data: String) {

    }

    override fun onFail(error: AppException?) {
    
    }
})
```