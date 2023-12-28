import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

declare var CodeMirror: any;
@Component({
  selector: 'app-coding',
  templateUrl: './coding.component.html',
  styleUrls: ['./coding.component.css']
})
export class CodingComponent implements OnInit {

  @ViewChild('editor', { static: false }) editorTextarea: ElementRef;

  private editor: any;

  constructor() { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    const width = window.innerWidth * 0.7;
    this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
      mode: "text/x-java",
      theme: "darcula",
      lineNumbers: true,
      autoCloseBrackets: true,
    });
    // this.editor.setSize(width, 500);
  }

}
