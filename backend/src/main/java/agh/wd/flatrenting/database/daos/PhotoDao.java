package agh.wd.flatrenting.database.daos;

import agh.wd.flatrenting.database.repositories.PhotoRepository;
import agh.wd.flatrenting.entities.Photo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class PhotoDao implements Dao<Photo> {

    private static Logger logger = Logger.getLogger(PhotoDao.class);

    private PhotoRepository photoRepository;

    @Autowired
    public PhotoDao(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public Optional<Photo> get(int id) {
        var optionalPhoto = photoRepository.findById(id);
        optionalPhoto.ifPresent(
                photo -> photo.setData(decompressBytes(photo.getData()))
        );

        return optionalPhoto;
    }

    @Override
    public List<Photo> getAll() {
        return null;
    }

    @Override
    public void save(Photo photo) {
        photo.setData(compressBytes(photo.getData()));
        photoRepository.save(photo);
    }

    @Override
    public void update(Photo photo) {

    }

    @Override
    public void delete(Photo photo) {
        photoRepository.findById(photo.getId()).ifPresent(
                photoRepository::delete
        );
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[2048];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
        logger.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[2048];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException exception) {
            logger.error(exception.getMessage());
        }
        return outputStream.toByteArray();
    }
}
