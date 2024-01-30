import { Injectable } from '@angular/core';

declare var PDFDocument:any;
@Injectable({
  providedIn: 'root'
})
export class PdfGeneratorService {
  
  async generatePdfFromJson(jsonData: any): Promise<Uint8Array> {
    const pdfDoc = await PDFDocument.create();
    const page = pdfDoc.addPage();
    const { width, height } = page.getSize();
    const fontSize = 12;

    const text = JSON.stringify(jsonData, null, 2);

    page.drawText(text, {
      x: 50,
      y: height - 50,
      font: await pdfDoc.embedFont(PDFDocument.Font.Helvetica),
      fontSize,
      color: '#fff',
    });

    return pdfDoc.save();
  }
}
