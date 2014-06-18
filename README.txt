NAME:
	SearchEngine
FUNCTION:
	A simple search engine.
VERAION:
	0.01
AUTHOR:
	jiangxin
Copyright (c) 2014, jiangxin


All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
Neither the name of the jiangxin nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Log:
	Version 0.01
		完成框架设计，效率和功能都有待优化。
	Version 0.02
		这是一次较大的修改，主要集中在爬虫方面，爬虫的灵活性更大，可以有选择性的爬取网页，效率更高，代码也更容易阅读。并将爬虫部分和html代码解析部分分离。整个程序的各个子程序的耦合性进一步下降。删除了部分冗余代码。并解决了搜索页面链接无效的bug。标记了之前代码中的不良代码，方便以后的进一步修改。
	Version 0.03
		更改了Web前端部分，简化了代码，去除了CSS只用无效的样式。优化了界面，更加简洁。删除了部分无用的资源（比如图片）