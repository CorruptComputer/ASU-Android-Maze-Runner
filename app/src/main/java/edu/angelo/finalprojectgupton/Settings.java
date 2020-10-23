package edu.angelo.finalprojectgupton;

import com.badlogic.androidgames.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {
    public static boolean soundEnabled = true;
    public final static int[] highscores = new int[] {100, 80, 50, 30, 10};

    public static void load(FileIO files) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
            files.readFile(".shootem")))) {
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i += 1) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            // :( It's ok we have defaults
        } catch (NumberFormatException e) {
            // :/ It's ok, defaults save our day
        }
    }

    public static void save(FileIO files) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
            files.writeFile(".shootem")))) {
            out.write(Boolean.toString(soundEnabled) + '\n');
            for (int i = 0; i < 5; i += 1) {
                out.write(Integer.toString(highscores[i]) + '\n');
            }
        } catch (IOException e) {
        }
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i += 1) {
            if (highscores[i] < score) {
                System.arraycopy(highscores, i, highscores, i + 1, 4 - i);
                highscores[i] = score;
                return;
            }
        }
    }
}
