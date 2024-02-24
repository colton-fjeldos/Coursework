import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.File;

public class main {
    private char array[] = new char[30000];
    private String instructions;
    private int index = 0;
    private int instruction_index = 0;
    private BufferedReader userInput;

    public static void main(String args[]){
        File inputFile = new File(args[0]);
        String instructions = "";
        try {
            Scanner fileScanner = new Scanner(inputFile);
            while (fileScanner.hasNextLine()){
                instructions = instructions + fileScanner.nextLine();
            }
            main interpreter_instance = new main(instructions);
            interpreter_instance.run();
            interpreter_instance.close();
        }
        catch (Exception e){
            System.out.println(e);
        }


    }

    public main(String instructions){
        this.instructions = instructions;
        userInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void close(){
        try {
            userInput.close();
        } catch (Exception e) {
            System.out.println("Why do I have to trycatch this");
        }
    }

    public void run(){
        while (instruction_index < instructions.length()){
            processcommand(instructions.charAt(instruction_index));
            instruction_index++;
        }
    }

    private void processcommand(char command){
        switch(command){
            case '>': right();
            break;
            case '<': left();
            break;
            case '+': increase();
            break;
            case '-': decrease();
            break;
            case '.': paste();
            break;
            case ',': grab();
            break;
            case '[': loop();
            break;
            case ']': unloop();
            break;
            default : break;
        }
    }

    private void right() {
        //System.out.println("Right");
        index++;
        if (index > 30000){
            index = 30000;
        }
    }

    private void left() {
        //System.out.println("Left");
        index--;
        if (index < 0){
            index = 0;
        }
    }

    private void increase(){
        //System.out.println("Increase");
        array[index]++;
    }

    private void decrease(){
        //System.out.println("Decrease");
        array[index]--;
    }

    private void paste(){
        System.out.print(array[index]);
    }

    private void grab(){
        try {
            array[index] = (char) userInput.read();
        } catch (Exception e) {
            System.out.println("Error: program lacks robustness");
        }
    }

    private void loop(){
        int bracket_count = 0;
        if (array[index] == 0){
            instruction_index++;
            while (bracket_count > 0 || instructions.charAt(instruction_index) != ']'){
                if (instructions.charAt(instruction_index) == '[') bracket_count++;
                if (instructions.charAt(instruction_index) == ']') bracket_count--;
                instruction_index++;
            }
        }
    }

    private void unloop(){
        int bracket_count = 0;
        if (array[index] != 0){
            instruction_index--;
            while (bracket_count > 0 || instructions.charAt(instruction_index) != '['){
                if (instructions.charAt(instruction_index) == '[') bracket_count--;
                if (instructions.charAt(instruction_index) == ']') bracket_count++;
                instruction_index--;
            }
        }
    }
}