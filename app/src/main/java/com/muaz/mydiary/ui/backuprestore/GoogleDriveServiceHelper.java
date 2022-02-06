package com.muaz.mydiary.ui.backuprestore;


import static com.muaz.mydiary.ui.others.BackUpAndRestore.folderId;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * A utility for performing creating folder if not present, get the file, upload the file, download the file and
 * delete the file from google drive
 */
public class GoogleDriveServiceHelper {

    private static final String TAG = "GoogleDriveService";
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;

    private final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";
  //  private final String SHEET_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

 //   private final String FOLDER_MIME_TYPE = "text/plain";
    private final String SHEET_MIME_TYPE = "text/plain";
    private final String FOLDER_NAME = "Dairy_Data_Folder";

    public GoogleDriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }


    /**
     * Check Folder present or not in the user's My Drive.
     */
    public Task<String> isFolderPresent() {
        return Tasks.call(mExecutor, () -> {
            FileList result = mDriveService.files().list().setQ("mimeType='application/vnd.google-apps.folder' and trashed=false").execute();
            for (File file : result.getFiles()) {
                if (file.getName().equals(FOLDER_NAME))
                    return file.getId();
            }
            return "";
        });
    }

    /**
     * Creates a Folder in the user's My Drive.
     */
    public Task<String> createFolder() {
        return Tasks.call(mExecutor, () -> {
            File metadata = new File()
                    .setParents(Collections.singletonList("root"))
                    .setMimeType(FOLDER_MIME_TYPE)
                    .setName(FOLDER_NAME);

            File googleFolder = mDriveService.files().create(metadata).execute();
            if (googleFolder == null) {
                throw new IOException("Null result when requesting Folder creation.");
            }

            return googleFolder.getId();
        });
    }

    /**
     * Get all the file present in the user's My Drive Folder.
     */
    public Task<ArrayList<GoogleDriveFileHolder>> getFolderFileList() {

        ArrayList<GoogleDriveFileHolder> fileList = new ArrayList<>();

        if (folderId.isEmpty()){
            Log.e(TAG, "getFolderFileList: folder id not present" );
            isFolderPresent().addOnSuccessListener(id -> folderId=id)
                    .addOnFailureListener(exception -> Log.e(TAG, "Couldn't create file.", exception));
        }

        return Tasks.call(mExecutor, () -> {
            FileList result = mDriveService.files().list()
                    .setQ("mimeType = '" + SHEET_MIME_TYPE + "' and trashed=false and parents = '" + folderId + "' ")
                    .setSpaces("drive")
                    .execute();

            for (int i = 0; i < result.getFiles().size(); i++) {
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                googleDriveFileHolder.setName(result.getFiles().get(i).getName());

                fileList.add(googleDriveFileHolder);
            }
            Log.e(TAG, "getFolderFileList: folderFiles: "+fileList );
            return fileList;
        });
    }


    /**
     * Upload the file to the user's My Drive Folder.
     */
    public Task<Boolean> uploadFileToGoogleDrive(String path) {

        if (folderId.isEmpty()){
            Log.e(TAG, "uploadFileToGoogleDrive: folder id not present" );
            isFolderPresent().addOnSuccessListener(id -> folderId=id)
                    .addOnFailureListener(exception -> Log.e(TAG, "Couldn't create file.", exception));
        }

        return Tasks.call(mExecutor, () -> {

            Log.e(TAG, "uploadFileToGoogleDrive: path: "+path );
            java.io.File filePath = new java.io.File(path);

            File fileMetadata = new File();
            fileMetadata.setName(filePath.getName());
            fileMetadata.setMimeType("json");

            FileList result = mDriveService.files().list().execute();

            fileMetadata.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent(SHEET_MIME_TYPE, filePath);
            File Createfile = mDriveService.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + Createfile.getId());

/*            if (result.size()>0){
                Log.e("mytag","if");
                for(File file: result.getFiles()) {
                    Log.e("mytag","for");
                    if (file.getName().equals(filePath.getName())){
                        File file1 = mDriveService.files().update(file.getId(), fileMetadata)
                                .execute();
                        System.out.println("File ID: " + file1.getId());
                        Log.e("mytag","if in");
                    }else {
                        Log.e("mytag","else in");
                    }
                    System.out.printf("Found file: %s (%s)\n",
                            file.getName(), file.getId());
                }
            }else {
                Log.e("mytag","else");
                fileMetadata.setParents(Collections.singletonList(folderId));
                FileContent mediaContent = new FileContent(SHEET_MIME_TYPE, filePath);
                File file = mDriveService.files().create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute();
                System.out.println("File ID: " + file.getId());
            }*/

            return false;
        });
    }
    public Task<Boolean> updateFileToGoogleDrive(String path,String f_id) {

        return Tasks.call(mExecutor, () -> {

            Log.e(TAG, "uploadFileToGoogleDrive: path: "+path );
            java.io.File filePath = new java.io.File(path);

            File fileMetadata = new File();
            fileMetadata.setName(filePath.getName());
            fileMetadata.setMimeType("json");

            FileList result = mDriveService.files().list().execute();

            fileMetadata.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent(SHEET_MIME_TYPE, filePath);
            File Createfile = mDriveService.files().update(f_id, fileMetadata)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + Createfile.getId());

/*            if (result.size()>0){
                Log.e("mytag","if");
                for(File file: result.getFiles()) {
                    Log.e("mytag","for");
                    if (file.getName().equals(filePath.getName())){
                        File file1 = mDriveService.files().update(file.getId(), fileMetadata)
                                .execute();
                        System.out.println("File ID: " + file1.getId());
                        Log.e("mytag","if in");
                    }else {
                        Log.e("mytag","else in");
                    }
                    System.out.printf("Found file: %s (%s)\n",
                            file.getName(), file.getId());
                }
            }else {
                Log.e("mytag","else");
                fileMetadata.setParents(Collections.singletonList(folderId));
                FileContent mediaContent = new FileContent(SHEET_MIME_TYPE, filePath);
                File file = mDriveService.files().create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute();
                System.out.println("File ID: " + file.getId());
            }*/

            return false;
        });
    }

    /**
     * Download file from the user's My Drive Folder.
     */
    public Task<Boolean> downloadFile(final java.io.File fileSaveLocation, final String fileId) {
        return Tasks.call(mExecutor, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // Retrieve the metadata as a File object.
                OutputStream outputStream = new FileOutputStream(fileSaveLocation);
                mDriveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                return true;
            }
        });
    }

    /**
     * delete file from the user's My Drive Folder.
     */
    public Task<Boolean> deleteFolderFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // Retrieve the metadata as a File object.
                if (fileId != null) {
                    mDriveService.files().delete(fileId).execute();
                    return true;
                }
                return false;
            }
        });
    }

}
