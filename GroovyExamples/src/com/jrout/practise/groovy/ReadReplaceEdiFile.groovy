package com.jrout.practise.groovy

import groovy.io.FileType
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

def list = []
def timeStampAfter = System.currentTimeMillis()
def dir = new File("C:\\Users\\jrout\\Documents\\_ProjectSpecific\\riverside\\IWAY-Integrations\\")
def builder = new AntBuilder();
while (true) {
    list = [];
    Thread.sleep(5000)
    def tempTimeStamp = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10)
    println " Changing files modified After : ${new Date(timeStampAfter)}"
    try {
        dir.eachFileRecurse(FileType.FILES) { file ->
            if (isTimeStampWithAnHr(file.lastModified(), timeStampAfter) && !file.name.endsWith(".xml")) {
                println "File : $file : TimeStamp : ${new Date(file.lastModified())}"
                builder.replace(file: file, token: "~", value: "\n")
                //list << file
            }
        }
        /*(list).each {
            builder.replace(file: it, token: "~", value: "\n")
        }*/
    } catch (Exception exp) {
        continue;
    }
    timeStampAfter = tempTimeStamp
}

def isSameDay(long fileModified) {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    return fmt.format(new Date(fileModified)).equals(fmt.format(new Date()));
}


def isTimeStampWithAnHr(long fileLastModified, long timeStamp) {
    return new Date(fileLastModified).after(new Date(timeStamp))
}