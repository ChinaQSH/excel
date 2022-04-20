import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;

public class ExcelDemo{
    public static void main(String[] args) {
        ExcelDemo obj = new ExcelDemo();
        File file = new File("src/main/resources/AMap_adcode_citycode_20210406.xls");
        obj.readExcel(file);
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public void readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                StringBuilder sb = new StringBuilder();
                String str1 = "";
                String str2 = null;
                String str3 = null;
                String type=null;
                Integer pid=0;
                int count = 0;
                //省份id
                int ppd=0;
                //市区id
                int mpd=0;

                for (int i = 1; i < sheet.getRows(); i++) {
                    // sheet.getColumns()返回该页的总列数
                    ++count;
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        if (j == 0) {
                            str1 = cellinfo;
                        } else if (j == 1) {
                            str2 = cellinfo;

//                            四个0 省份
                            if (cellinfo.substring(cellinfo.length()-4).equals("0000")){
                                ppd=i;
                            }
//                            两个0 市
                            if (cellinfo.substring(cellinfo.length()-2).equals("00")){
                                pid=ppd;
                                mpd=i;
                                type="city";
                            }else{
                                pid=mpd;
                                type="county";
                            }
                        } else {
                            str3 = cellinfo;
                            if (str3.equals("")){
                                type="province";
                                pid=1;
                            }
                        }
                    }
                    StringBuffer sql=new StringBuffer();
                    sql.append("insert into nc_area (id,name,type,pid) values (");
                    sql.append(i);
                    sql.append(",'");
                    sql.append(str1);
                    sql.append("','");
                    sql.append(type);
                    sql.append("',");
                    sql.append(pid);
                    sql.append(");");
                    System.out.println(sql.toString());
                    writer(sql);
                    sb.setLength(0);
                    str1 = "";
                    str2 = "";
                    str3 = "";
                }
                System.out.println("总字段数："+count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * 写sql写入文件
     * @param buffer
     * @throws Exception
     */
    private static void writer(StringBuffer buffer) throws Exception{
        PrintStream out = null ;
        try{
            out = new PrintStream(new FileOutputStream(new File("src/main/resources/area.sql"),true)) ;
            System.err.println(buffer.toString());
            out.println(buffer.toString());

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(out!=null){
                out.close();
            }
        }
    }

}

//id  int  comment'主键id',
//name  varchar  comment'姓名',
//age  int  comment'年龄',
//总字段数：3
//总字段数：0
//总字段数：0
 
 