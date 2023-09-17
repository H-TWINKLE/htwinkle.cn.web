package cn.htwinkle.web.render;

import cn.htwinkle.web.kit.ContentTypeKit;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.jfinal.render.Render;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MimeTypeRender extends Render {
    private String filename;
    private boolean download;
    private InputStream inputStream;

    public MimeTypeRender(String filename, InputStream inputStream, boolean download) {
        this.filename = filename;
        this.download = download;
        this.inputStream = inputStream;
    }

    public MimeTypeRender(String filename, InputStream inputStream) {
        this.filename = filename;
        this.inputStream = inputStream;
        this.download = false;
    }

    public void render() {
        String fileName = FileUtil.getName(filename);
        // 设置头信息,内容处理的方式,attachment以附件的形式打开,就是进行下载,并设置下载文件的命名
        if (download) {
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        }
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType(ContentTypeKit.get(FileUtil.getSuffix(filename)));

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            IoUtil.copy(inputStream, out, IoUtil.DEFAULT_BUFFER_SIZE);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new UtilException(e);
        } finally {
            IoUtil.close(inputStream);
        }
    }
}
