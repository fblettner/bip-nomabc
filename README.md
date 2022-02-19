# NOMABC

# Simple JAVA CLASS to encode string and display barcode 128 with BI Publisher RTF template

Steps to use this java class

1) Modify xdo.cfg 
Add the line below to declare the truetype font. The font is delivered into the dist directory. (Thanks to GrandZebu for explanation and font : http://grandzebu.net/informatique/codbar/code128.htm)

\<font family="Code 128z" style="normal" weight="normal"><truetype path="d:/BIP/java/fonts/code128z.TTF"/></font>


2) Declaration of a custom function into a RTF Template
Add a field into the template and set the property like this

\<?register-barcode-vendor:'nomabc.BarcodeUtil';'NOMANA’?>

![image](https://user-images.githubusercontent.com/11517744/154798056-2ae9f26c-b15a-450d-8416-81c966975df2.png)

3) Convert into BarCode 128
Add the field with the value to convert and modify the property with the following script

\<?format-barcode:DataBarreCode;'code128';'NOMANA’?>

![image](https://user-images.githubusercontent.com/11517744/154798079-b290c7d2-9c35-4a17-8756-5a6b4c516827.png)


4) Display the BarCode
Modify the font for the field in RTF with and select "Code 128z". If the truetype is not declared into Windows fonts, you will not see the font but you can simply modify it by typing the font name as screenshot below

![image](https://user-images.githubusercontent.com/11517744/154797985-cc8c7194-44b8-466c-9dba-93df3271eb97.png)
