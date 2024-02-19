package com.example.starterproject.utils.api_handler.exception

/**
 * ServerErrorException is thrown when api response code is 500..599
 * No need message
 * Action/Message must be determined in UI layer
 */
class ServerErrorException: Exception()