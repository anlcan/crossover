package com.crossover.application;


import com.amazonaws.auth.AWSCredentials;


/**
 *   This Config typically read from a config file.
 */
public class Config {

    /**
     * S3 bucket name
     */
    public static final String BUCKET_NAME = "crossover.assignment";

    /**
     *   Writer credentials for S3
     */
    public static final AWSCredentials WRITER_CREDENTIALS = new AWSCredentials(){


        public String getAWSAccessKeyId() {
            return "AKIAJGDA52JUP2QH4UWQ";
        }

        public String getAWSSecretKey() {
            return "T83EHNUxuZgC83mKc30ZHiu0znRwfUWVu6XeOTYb";
        }
    };




}
