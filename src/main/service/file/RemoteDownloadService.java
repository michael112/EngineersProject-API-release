package main.service.file;

public interface RemoteDownloadService {

    byte[] getFile(String remoteID) throws Exception;

}
