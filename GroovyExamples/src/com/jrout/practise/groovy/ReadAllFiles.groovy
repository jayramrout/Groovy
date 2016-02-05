package com.jrout.practise.groovy
import groovy.io.FileType

/**
 * Created by jrout on 1/30/2016.
 */
def dir = new File("C:\\Users\\jrout\\Documents\\_ProjectSpecific\\riverside\\")

dir.eachFileRecurse (FileType.FILES) { file ->
	println file
}