using design_client.Common;
using design_client.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace design_client.Http
{
    public class ApiException: Exception
    {

        public int Code { get; set; }
        public object? Value { get; set; }

        public ApiException(string msg,int code,object? value) : base(msg) {
            this.Code = code;
            this.Value = value;
        }

        public ApiException(ErrorBody body) : base(body.Msg)
        {
            this.Code = body.Code;
            this.Value = body.Data;
        }
    }
}
