package in.ac.bits_hyderabad.swd.swd.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.ac.bits_hyderabad.swd.swd.R;

public class PdfDocumentSample {

    private String idNo;
    private String title;
    private String sem;
    private String date;
    private String content;
    private String dean;
    private String post;
    private String address;
    private String contact;
    private Image image_header, image_footer;
    private Context context;
    Activity activity;

    TextView tvTitle,tvProgress;
    ProgressBar pbProgress;
    public int MY_PERMISSIONS_REQUEST_WRITE_EXT_STORAGE=1000;

    Dialog dialog;

    public PdfDocumentSample(String idNo, String title, String content, String dean, String post, String address, String contact, Context context) {

        Date current_date = Calendar.getInstance().getTime();
        String today = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH).format(current_date);
        int current_year = Integer.parseInt(today.substring(2, 4));
        String semester = Integer.parseInt(today.substring(5, 7)) > 7 ? "I" : "II";
        int pr_year=current_year-1;
        int next_year=current_year+1;

        this.idNo = idNo;
        this.title = title;
        this.content = content;
        this.sem = "BITS/HYD/SWD/20" + (semester.equals("II") ? pr_year + "-" + current_year : current_year + "-" + next_year) + "/Semester-" + semester;
        this.date = "Date - " + today.substring(8, 10) + today.substring(4, 8) + today.substring(0, 4);
        this.dean = dean;
        this.post = post;
        this.address = address;
        this.contact = contact;
        this.context = context;
        activity=(Activity)context;
    }

    public void downloadFile()
    {


            dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_download);
            tvTitle = dialog.findViewById(R.id.tvTitleDownload);
            tvProgress = dialog.findViewById(R.id.tvProgress);
            pbProgress = dialog.findViewById(R.id.pbProgress);
            pbProgress.setMax(100);
            dialog.create();

            tvTitle.setText(title.toUpperCase());

            dialog.show();
            new DownloadDoc().execute();
    }



    public class DownloadDoc extends AsyncTask<Void, Integer, String> {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SWD Documents";
        FileOutputStream fOut;
        PdfDocument pdfDocument;
        Paragraph p_title, p_prof;
        Table tablehead;
        Table tablefoot;
        Paragraph p_content;
        File file;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                //getting document ready
                publishProgress(0);

                File dir = new File(path);
                Log.e("made dir"," ");

                if(!dir.exists())
                {
                    dir.mkdirs();
                }
                File chk=new File(path+"/"+idNo+"-"+title+".pdf");
                if(chk.exists()){
                    Log.e("file already exists","");
                    chk.delete();
                }

                //int r= new Random().nextInt(99);
                file = new File(dir, idNo+"-"+title+".pdf");

                fOut = new FileOutputStream(file);
                PdfWriter writer=new PdfWriter(fOut);
                pdfDocument=new PdfDocument(writer);
                Document document=new Document(pdfDocument,PageSize.A4);
                document.getPdfDocument().addNewPage();
                document.setFontSize(15f);

                //getting elements ready

                //images
                image_header=new Image(ImageDataFactory.create("http://swd.bits-hyderabad.ac.in/components/navbar/logo_long.jpg"));
                document.add(image_header);

                publishProgress(20);

                float width = PageSize.A4.getWidth();
                float height = PageSize.A4.getHeight();

                //border
                PdfCanvas canvas = new PdfCanvas(pdfDocument.getFirstPage());
                canvas.rectangle(20, 20, width - 40, height - 40);
                canvas.rectangle(18,18,width-36,height-36);
                canvas.stroke();

                publishProgress(25);

                //title para
                p_title=new Paragraph();
                p_title.setMarginTop(20f).setFontSize(20f).setBold()
                        .add(title.toUpperCase()).setUnderline()
                        .setMarginBottom(20f).setTextAlignment(TextAlignment.CENTER);
                document.add(p_title);

                publishProgress(30);

                //table with semester and date info
                float colWidHeader[]={(width-60)/2,(width-60)/2};
                tablehead=new Table( colWidHeader);
                tablehead.addCell(new Paragraph(sem)).setFontSize(13f).getCell(0,0)
                        .setBorder(new SolidBorder(Color.WHITE,4)).setTextAlignment(TextAlignment.LEFT);
                tablehead.addCell(new Paragraph(date)).setFontSize(13f).getCell(0,1)
                        .setBorder(new SolidBorder(Color.WHITE,4)).setMarginBottom(20f).setTextAlignment(TextAlignment.RIGHT);

                document.add(tablehead);

                publishProgress(35);

                //content para
                p_content = new Paragraph (content).setMarginBottom(30f).setMarginLeft(4f);
                document.add(p_content);

                publishProgress(60);

                //professor_para
                p_prof=new Paragraph();
                p_prof.add(dean+"\n").setFontSize(17f).setTextAlignment(TextAlignment.RIGHT);
                p_prof.add(post).setFontSize(17f).setTextAlignment(TextAlignment.RIGHT);
                document.add(p_prof);

                publishProgress(70);

                //footer table
                image_footer=new Image(ImageDataFactory.create("http://swd.bits-hyderabad.ac.in/components/navbar/noc.png"));
                float colWidfooter[]={(width-60)/3-40,(width-60)/3+40,(width-60)/3};
                tablefoot=new Table( colWidfooter);
                tablefoot.addCell(image_footer.scaleAbsolute((width-60)/3,50f)).getCell(0,0).setBorderRight(new SolidBorder(Color.WHITE,4));
                tablefoot.addCell(new Paragraph(address)
                        .setMarginLeft(10f).setFontSize(8f)).getCell(0,1).setBorderRight(new SolidBorder(Color.WHITE,4));
                tablefoot.addCell(new Paragraph(contact)
                        .setMarginLeft(10f).setFontSize(8f)).getCell(0,2).setBorderRight(new SolidBorder(Color.WHITE,4));
                tablefoot.setFixedPosition(30f,30f,(width-60));
                tablefoot.setBorder(new SolidBorder(Color.WHITE,4));
                document.add(tablefoot);

                publishProgress(100);


                document.close();
                fOut.close();

                Log.e("doc closed","");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            pbProgress.setProgress(values[0]);
            tvProgress.setText(values[0]+"%");
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            dialog.cancel();
            Toast.makeText(context,"Downloaded at "+file.getPath(),Toast.LENGTH_SHORT).show();

            File open=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SWD Documents/"+idNo+"-"+title+".pdf");
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(FileProvider.getUriForFile(context,context.getPackageName()+".fileprovider",open),"application/pdf");
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);



            try {
                context.startActivity(Intent.createChooser(target, "Open File Using..."));
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }

            super.onPostExecute(s);
        }

      /*  @Override
        protected void onCancelled() {
            file.delete();
            super.onCancelled();
        }*/
    }
}
