package jin.translate;

import com.google.gson.Gson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import org.apache.http.util.TextUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jin on 2017/5/8
 */
public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Editor mEditor=e.getData(PlatformDataKeys.EDITOR);
        if (null == mEditor) {
            return;
        }
        SelectionModel model=mEditor.getSelectionModel();
        String selectedText=model.getSelectedText();
        if (TextUtils.isEmpty(selectedText)) {
            return;
        }
        translate(mEditor,selectedText);
    }
    private void translate(Editor editor, String text){
        String baseUrl="http://fanyi.youdao.com/openapi.do?keyfrom=progresstranslate&key=1316007487&type=data&doctype=json&version=1.1&q=";

        new Thread(() -> {

            try {
                URL url=new URL(baseUrl+text);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                connection.setConnectTimeout(15000);
                connection.setConnectTimeout(5000);
                connection.connect();
                if (200  == connection.getResponseCode()) {
                    InputStream inputStream=connection.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                    BufferedReader reader=new BufferedReader(inputStreamReader);
                    StringBuilder stringBuffer=new StringBuilder();
                    String string;
                    while ((string=reader.readLine())!=null){
                        stringBuffer.append(string);
                    }
                    Gson gson=new Gson();
                    Translation translation=gson.fromJson(stringBuffer.toString(),Translation.class);
                    StringBuilder showText;
                    try{
                        showText = new StringBuilder(translation.getQuery() + ":" + translation.getTranslation() + "\n" + translation.getBasic().getExplains().toString()
                                + "\n");
                        if (translation.getWeb()!=null&&translation.getWeb().size()>0) {
                            for (int i = 0; i < translation.getWeb().size(); i++) {
                                showText.append(translation.getWeb().get(i).getKey()).append("\n").append(translation.getWeb().get(i).getValue()).append("\n");
                            }
                        }
                    }catch (Exception e){
                        showText = new StringBuilder("无网络释义");
                    }


                    showPopUp(editor, showText.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void showPopUp(Editor editor, String result){
        ApplicationManager.getApplication().invokeLater(() -> {
            JBPopupFactory factory=JBPopupFactory.getInstance();
            factory.createHtmlTextBalloonBuilder(result,null,  new JBColor(new Color(186, 238, 186), new Color(73, 117, 73)),null)
                    .setFadeoutTime(5000)
                    .createBalloon()
                    .show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
        });
    }
}
