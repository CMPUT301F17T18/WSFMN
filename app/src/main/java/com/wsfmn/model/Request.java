package com.wsfmn.model;

/**
 * Created by Fredric on 2017-10-21.
 */

/**
 * Class that extends ProfileName, A Request Holding the User's name, and a search name
 * to find another user, and a Request type of either "follow" or "share".
 *
 * @see ProfileName
 */
public class Request extends ProfileName{
    private String searchName;  // this name will be used to search for
    private String requestType; // Just a follow type, But can be extended to sharing or other in the future

    /**
     * Construct to create a request, using ProfileName's Construct
     * @param name
     */
    public Request(String name) {
        super(name);
    }

    /**
     * Construct to create a request, having User's name, a Search Name and the request type.
     * @param name
     * @param searchName
     * @param requestType
     */
    public Request(String name, String searchName, String requestType){
        super(name);
        this.searchName = searchName;
        this.requestType = requestType;
    }

    /**
     * Method to receive the searchName from Request
     * @return
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * Method to set the searchName for Request
     * @param searchName
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * Method to get the type of Request from Request.
     * @return
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Method to set the type of Request for Request.
     * @param requestType
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}