package pomodoro;


import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Collections.fill;

public class pomodoro{
    private final JMenuBar menubar;
    private JPanel panelMain;
    private JButton buttonStart;
    private JButton buttonStop;
    private JLabel labelMsg;
    private JLabel labelTimer;
    private JPanel panelButtons;
    private JProgressBar progressBar1;
    private long focusMinutes;
    private final JFrame frame;

    enum State {
        BOOTED,
        FOCUS,
        BREAK
    }

    private State state = State.BOOTED;
    private boolean timerStopped = false;




    // SETTERS & GETTERS
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTimerStopped(boolean timerStopped) {
        this.timerStopped = timerStopped;
    }

    public long getFocusMinutes() {
        return focusMinutes;
    }

    public void setFocusMinutes(long focusMinutes) {
        this.focusMinutes = focusMinutes;
    }

    public long getBreakMinutes() {
        return 5;
    }

    public void setLabelMsg(String message) {
        this.labelMsg.setText(message);
    }

    public void setLabelTimer(String timeRemaining) {
        this.labelTimer.setText(timeRemaining);
    }

    public void setEnableButtonStart(boolean enabled) {
        this.buttonStart.setEnabled(enabled);
    }

    public void setEnableButtonStop(boolean enabled) {
        this.buttonStop.setEnabled(enabled);
    }

    public boolean isTimerStopped() {
        return this.timerStopped;
    }
    // END SETTERS AND GETTERS

    // Creates a dialogue that asks the user for the focus period.
    private void requestFocusTime() {
        String m = JOptionPane.showInputDialog(com.codebind.Locale.TIME_QUESTION_MESSAGE);

        int convertedMinutes;
        try {
            convertedMinutes = Integer.parseInt(m);
        } catch (Exception exception) {
            convertedMinutes = 25;
        }

        if (convertedMinutes < 1 || convertedMinutes > 60) {
            convertedMinutes = 25;
        }
        setFocusMinutes(convertedMinutes);
    }

    // Edits the message and the timer labels.
    private void resetTimerAndMessage(String message) {
        setLabelMsg(message);
        setEnableButtonStart(true);
        setEnableButtonStop(false);
        setTimerStopped(false);
        setLabelTimer("00:00");
    }

    public void finishSessionAndDisplayMessage(String message, JFrame frame) {
        flashWindow(frame);
        com.codebind.Utils Utils;
        com.codebind.Utils.playAlarmSound();

        JOptionPane.showMessageDialog(null, message);
    }

    private void startFocusPeriod() {

        setLabelMsg(com.codebind.Locale.FOCUS_MESSAGE);
        setEnableButtonStop(true);
        setEnableButtonStart(false);
        setTimerStopped(false);
    }

    private void endFocusPeriod() {
        // Ended the focus, we need to take a break now.
        setState(State.BREAK);

        resetTimerAndMessage(com.codebind.Locale.FOCUS_DONE_MESSAGE);
        finishSessionAndDisplayMessage(com.codebind.Locale.FOCUS_DONE_MESSAGE_ALERT, frame);
    }

    private void startBreakPeriod() {
        setLabelMsg(com.codebind.Locale.BREAK_MESSAGE);
        setEnableButtonStop(true);
        setEnableButtonStart(false);
        setTimerStopped(false);
    }

    private void endBreakPeriod() {
        // Ended the break, we need to now.
        setState(State.FOCUS);

        resetTimerAndMessage(com.codebind.Locale.BREAK_DONE_MESSAGE);
        finishSessionAndDisplayMessage(com.codebind.Locale.BREAK_DONE_MESSAGE_ALERT, frame);
    }

    public static void flashWindow(JFrame frame) {
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(false);
    }

    private void startTimerTask() {
       /* long startTime = System.currentTimeMillis();
        Timer timer = new Timer();

        // Pick whether we choose to subtract from session or break time.
        long sessionMinutes = 0;
        if (getState() == State.BOOTED || getState() == State.FOCUS) {
            setState(State.FOCUS);
            sessionMinutes = getFocusMinutes();
        }
        else if (getState() == State.BREAK) {
            sessionMinutes = getBreakMinutes();
        }

        long finalSessionMinutes = sessionMinutes;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long systemTime = System.currentTimeMillis();

                // If the timer is below zero, or it has been stopped, cancel the updates.
                if (systemTime - startTime > finalSessionMinutes * 60 * 1000 || isTimerStopped()) {
                    this.cancel();
                    if (getState() == State.BOOTED) {
                        resetTimerAndMessage(com.codebind.Locale.GREET_MESSAGE);
                    }
                    else if (getState() == State.FOCUS) {
                        endFocusPeriod();
                    }
                    else if (getState() == State.BREAK) {
                        endBreakPeriod();
                    }
                }
                // Update the timer with the new remaining time.
                else {
                    setLabelTimer(com.codebind.Utils.convertRemainingMillisToMinutes(systemTime - startTime, finalSessionMinutes));
                }
            }
        }, 0, 100);*/

            long startTime = System.currentTimeMillis();
            Timer timer = new Timer();

            long sessionMinutes = getState() == State.BREAK ? getBreakMinutes() : getFocusMinutes();
            long sessionTimeInMillis = sessionMinutes * 60 * 1000; // Convert session time to milliseconds

            progressBar1.setMaximum(100); // Ensure the progress bar max is set to 100 for percentage calculation

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime >= sessionTimeInMillis || isTimerStopped()) {
                        this.cancel();
                        progressBar1.setValue(100); // Ensure progress bar is complete at the end of the session
                        if (getState() == State.BOOTED) {
                            resetTimerAndMessage(com.codebind.Locale.GREET_MESSAGE);
                        } else if (getState() == State.FOCUS) {
                            endFocusPeriod();
                        } else if (getState() == State.BREAK) {
                            endBreakPeriod();
                        }
                    } else {
                        int progressPercentage = (int) ((elapsedTime * 100) / sessionTimeInMillis);
                        progressBar1.setValue(progressPercentage);

                        // Update the timer label here with the remaining time
                        setLabelTimer(com.codebind.Utils.convertRemainingMillisToMinutes(elapsedTime, sessionMinutes));
                    }
                }
            }, 0, 1000); // Update every second
        }



    private void initFrame(JFrame frame) {
        frame.toFront();
        frame.setContentPane(this.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Pomodoro app");
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 300);

    }

    private void initMenuBar(JMenuBar menubar, JFrame frame) {
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem usageItem = new JMenuItem("Usage");
        JMenu helpMenu = new JMenu("Help");

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, com.codebind.Locale.ABOUT_MESSAGE);
            }
        });

        usageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, com.codebind.Locale.USAGE_MESSAGE);
            }
        });

        helpMenu.add(aboutItem);
        helpMenu.add(usageItem);

        JMenuItem resetItem = new JMenuItem("Reset");
        JMenu settingsMenu = new JMenu("Settings");

        resetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.BOOTED);
                setTimerStopped(true);

                JOptionPane.showMessageDialog(null, com.codebind.Locale.RESET_MESSAGE);
            }
        });

        settingsMenu.add(resetItem);

        menubar.add(settingsMenu);
        menubar.add(helpMenu);
        frame.setJMenuBar(menubar);
    }

    private void initButtons(JButton buttonStart, JButton buttonStop) {
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.codebind.Utils.playClickSound();

                if (getState() == State.FOCUS || getState() == State.BOOTED) {
                    // Only request the focus time the first time.
                    if (getState() == State.BOOTED)
                        requestFocusTime();
                    startFocusPeriod();

                    startTimerTask();
                }
                else if (getState() == State.BREAK) {
                    startBreakPeriod();

                    startTimerTask();
                }
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.codebind.Utils.playClickSound();

                setTimerStopped(true);
            }
        });
    }

    public pomodoro() {
        this.frame = new JFrame();
        initFrame(this.frame);

        this.menubar = new JMenuBar();
        initMenuBar(this.menubar, this.frame);

        // Start button logic
        initButtons(this.buttonStart, this.buttonStop);
    }

    public static void main(String[] args) {
        new pomodoro();
    }

    public class Utils {
    }
}
