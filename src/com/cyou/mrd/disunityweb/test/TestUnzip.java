package com.cyou.mrd.disunityweb.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.cyou.mrd.disunityweb.extraction.uzip.AntZip;

public class TestUnzip {

		public static void main(String args[]) throws IOException {
			String zipPath = "D:\\workspace_unity\\unzip\\df_dev_orange.ipa";
			String unzipPath = "D:\\workspace_unity\\unzip";
			
			AntZip zip = new AntZip(); 
			zip.unZip(zipPath,unzipPath);
			
		}

}
