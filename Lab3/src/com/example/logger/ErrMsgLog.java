package com.example.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author hottabych04
 */
public class ErrMsgLog {
    private static ArrayList <Exception> ErrList;
    private static Logger log;

    public ErrMsgLog() {
        ErrList = new ArrayList();
        //Читаем конфигурационный файл лога
        try {
            LogManager.getLogManager().readConfiguration(ErrMsgLog.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log = Logger.getLogger(ErrMsgLog.class.getName());
    }

    //Добавляем ошибку в список ошибок
    public int addErr(Exception e) {
        ErrList.add(e);
        return ErrList.size();
    }

    //Добавляем ошибку в список ошибок и в лог
    public int addErrWithLog(Exception e) {
        ErrList.add(e);

        //Код записи сообщения в лог
        log.log(Level.FINE, e.getMessage());
        return ErrList.size();
    }

    //Получаем количество ошибок
    public int getErrCount() {
        return ErrList.size();
    }

    //Выводим информацию об ошибке
    public void showErrText(Exception e) {
        System.err.println(e.getMessage());
    }

    //Генерим (пробрасываем ошибку) с фиксацией в списке ошибок
    public Exception makeErr(Exception e) {
        addErr(e);
        return new Exception(e);
    }
}