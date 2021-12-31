package com.tarzan.reptile.videoImage;

import lombok.Data;

import java.io.*;
import java.nio.charset.Charset;

@Data
public class Cartoon {

    private String inputDir;
    private String outputDir;
    private String device;
    private String checkpoint;

    public void setCheckpoint(int i) {
        String checkpointPt="celeba_distill.pt";
        switch(i){
            case 1 :
                break;
            case 2 :
                checkpointPt="face_paint_512_v1.pt";
                break;
            case 3 :
                checkpointPt="face_paint_512_v2.pt";
                break;
            case 4 :
                checkpointPt="paprika.pt";
                break;
            default :
                break;
        }
        this.checkpoint = checkpointPt;
    }

    public String getCommand(){
        return  "python test.py --input_dir "+inputDir+" --output_dir "+outputDir+" --device "+device+" --checkpoint ./weights/"+checkpoint;
    }

    //anaconda3环境bat路径
    private static String anacondaBatPath="D:\\anaconda3\\Scripts\\activate.bat";
    //animegan2-pytorch项目根目录
    private static String projectPath="E:\\animegan2-pytorch-main";

    public static void main(String[] args) throws IOException, InterruptedException {
        Cartoon param=new Cartoon();
        param.setInputDir("C:/Users/liuya/Desktop/image/8.png");
        param.setOutputDir("C:/Users/liuya/Desktop/cartoon/");
        //cuda 或 cpu
        param.setDevice("cpu");
        //
        param.setCheckpoint(3);
        run(param.getCommand());
    }



    public static void run(String command) {
        String baseCmd = "cmd /k "+anacondaBatPath+"  & conda activate pytorch & cd "+projectPath;
        try {
            Process proc = Runtime.getRuntime().exec(baseCmd+"&"+command);
            consumeInputStream(proc.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *   消费inputstream，并返回
     */
    public static String consumeInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));
        String s ;
        StringBuilder sb = new StringBuilder();
        while((s=br.readLine())!=null){
            System.out.println(s);
            sb.append(s);
        }
        return sb.toString();
    }

}
