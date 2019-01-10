/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

/**
 *
 * @author anangmb
 */
public class Helper {
    public Helper(){}
    String specialChar = ",./;'[]\\`!@#$%^&*()_+-=<>?:\"{}| \n";
    
    public Boolean isSpecialCharacter(char c){
        Boolean state = false;
        for(int i = 0;i < specialChar.length();i++){
            if(c == specialChar.charAt(i)){
                state = true;
                break;
            }
            else{
                state = false;
            }
        }
        return state;
    }
    public Boolean isSpaceChar(char charInput){
        String spaceHelper = " ";
        char helper = spaceHelper.charAt(0);
        char input = charInput;
        if(helper == input){
        return true;
        }else{
        return false;
        }
    }
}
