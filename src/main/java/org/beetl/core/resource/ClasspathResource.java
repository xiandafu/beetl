/*
 [The "BSD license"]
 Copyright (c) 2011-2019  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;

/**
 * @author xiandafu
 */
public class ClasspathResource extends Resource<String> {
    String path = null;

    File file = null;
    long lastModified = 0;

    public ClasspathResource(String key, String path, ResourceLoader resourceLoader) {
        super(key, resourceLoader);
        this.path = path;
    }

    @Override
    public Reader openReader() {
        ClasspathResourceLoader loader = ((ClasspathResourceLoader) resourceLoader);
        ClassLoader cs = loader.getClassLoader();
        URL url = cs.getResource(path);
        if (url == null) {
            //兼容以前的写法
            url = resourceLoader.getClass().getResource(path);
        }

        if (url == null) {
            BeetlException be = new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR);
            be.pushResource(this);
            throw be;
        }
        InputStream is;
        try {
            is = url.openStream();
        } catch (IOException e1) {
            BeetlException be = new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR);
            be.pushResource(this);
            throw be;
        }

        if (is == null) {
            BeetlException be = new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR);
            be.pushResource(this);
            throw be;
        }

        if (url.getProtocol().equals("file")) {
            try {
                file = new File(java.net.URLDecoder.decode(url.getFile(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            lastModified = file.lastModified();
        }

        try {
            InputStreamReader isr = new InputStreamReader(is, ((ClasspathResourceLoader) this.resourceLoader).charset);
            return new BufferedReader(isr);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public boolean isModified() {
        return (file != null) && (file.lastModified() != lastModified);
    }

    @Override
    public String getId() {
        return id;
    }

}
