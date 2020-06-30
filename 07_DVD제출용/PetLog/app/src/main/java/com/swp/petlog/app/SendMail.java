package com.swp.petlog.app;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMail extends AppCompatActivity {
    String user = "lksy1294@gmail.com"; // 보내는 계정의 id
    String password = "nebkwibimbkrolwm"; // 보내는 계정의 pw
    private static String code = GmailSender.createEmailCode(); // 인증번호
    public static String getCode(){
        return code;
    }
    public void sendSecurityCode(Context context, String sendTo) {
        try {
            GmailSender gMailSender = new GmailSender(user, password);
            gMailSender.sendMail("[인증번호] PetLog 어플리케이션 비밀번호 찾기 인증메일입니다.",code, sendTo);
            Toast.makeText(context, "이메일로 인증번호를 보냈습니다.",
                    Toast.LENGTH_SHORT).show();
        } catch (SendFailedException e) {
            Toast.makeText(context, "이메일 형식이 잘못되었습니다!",
                    Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(context, "인터넷 연결을 확인해주세요!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "이메일 전송 실패!",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
