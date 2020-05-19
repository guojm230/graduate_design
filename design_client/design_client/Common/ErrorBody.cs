using System;
using System.Collections.Generic;
using System.Text;

namespace design_client.Common
{
    public class ErrorBody
    {
        public int Code { get; set; }
        public string Msg { get; set; } = "";
        public object? Data { get; set; }
    }
}
