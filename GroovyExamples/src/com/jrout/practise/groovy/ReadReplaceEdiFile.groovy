package com.jrout.practise.groovy

import groovy.io.FileType
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

def list = []
def timeStampAfter = System.currentTimeMillis()
def dir = new File("C:\\Users\\jrout\\Documents\\_ProjectSpecific\\riverside\\IWAY-Integrations\\")
while (true) {
    list = [];
    Thread.sleep(3000)
    def tempTimeStamp = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10)
    println " Changing files modified After : ${new Date(timeStampAfter)}"
    try {
        dir.eachFileRecurse(FileType.FILES) { file ->
            if (isTimeStampWithAnHr(file.lastModified(), timeStampAfter) && !file.name.endsWith(".xml")) {
                println "File : $file : TimeStamp : ${new Date(file.lastModified())}"

                def shouldReplace = false;
                def count = 0;
                def stringBuffer = new StringBuffer();
                def endsWithCount = 0;
                file.eachLine { line ->
                    if(count++ < 4 ){
                        shouldReplace = line.contains("~");
                        if(line.endsWith("~")){
                            endsWithCount++;
                        }
                        if(endsWithCount > 2){
                            shouldReplace = false;
                            return;
                        }
                    }else if (!shouldReplace){
                        return;
                    }
                    stringBuffer << line
                }
                if(shouldReplace){
                    file.newWriter().withWriter { w ->
                        w << stringBuffer.replaceAll("~","~\n")
                    }
                }
            }
        }
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